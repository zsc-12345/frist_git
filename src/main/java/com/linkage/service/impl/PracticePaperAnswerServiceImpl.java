package com.linkage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linkage.domain.*;
import com.linkage.domain.enums.ExamPaperAnswerStatusEnum;
import com.linkage.domain.enums.ExamPaperTypeEnum;
import com.linkage.domain.enums.QuestionTypeEnum;
import com.linkage.domain.exam.ExamPaperTitleItemObject;
import com.linkage.domain.other.ExamPaperAnswerUpdate;
import com.linkage.domain.other.KeyValue;
import com.linkage.domain.task.TaskItemAnswerObject;
import com.linkage.repository.*;
import com.linkage.service.PracticePaperAnswerService;
import com.linkage.service.PracticePaperQuestionCustomerAnswerService;
import com.linkage.service.TextContentService;
import com.linkage.utility.DateTimeUtil;
import com.linkage.utility.ExamUtil;
import com.linkage.utility.JsonUtil;
import com.linkage.viewmodel.admin.paper.PracticePaperAnswerPageRequestVM;
import com.linkage.viewmodel.student.Practice.PracticePaperSubmitItemVM;
import com.linkage.viewmodel.student.Practice.PracticePaperSubmitVM;
import com.linkage.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.linkage.viewmodel.student.exam.ExamPaperSubmitVM;
import com.linkage.viewmodel.student.practicepaper.PracticePaperAnswerPageVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PracticePaperAnswerServiceImpl extends BaseServiceImpl<PracticePaperAnswer> implements PracticePaperAnswerService {

    private final PracticePaperAnswerMapper practicePaperAnswerMapper;
    private final PracticeMapper practicePaperMapper;
    private final TextContentService textContentService;
    private final QuestionMapper questionMapper;
    private final PracticePaperQuestionCustomerAnswerService practicePaperQuestionCustomerAnswerService;
    private final TaskExamCustomerAnswerMapper taskExamCustomerAnswerMapper;

    @Autowired
    public PracticePaperAnswerServiceImpl(PracticePaperAnswerMapper practicePaperAnswerMapper, PracticeMapper practicePaperMapper, TextContentService textContentService, QuestionMapper questionMapper, PracticePaperQuestionCustomerAnswerService practicePaperQuestionCustomerAnswerService, TaskExamCustomerAnswerMapper taskExamCustomerAnswerMapper) {
        super(practicePaperAnswerMapper);
        this.practicePaperAnswerMapper = practicePaperAnswerMapper;
        this.practicePaperMapper = practicePaperMapper;
        this.textContentService = textContentService;
        this.questionMapper = questionMapper;
        this.practicePaperQuestionCustomerAnswerService = practicePaperQuestionCustomerAnswerService;
        this.taskExamCustomerAnswerMapper = taskExamCustomerAnswerMapper;
    }

    @Override
    public PageInfo<PracticePaperAnswer> studentPage(PracticePaperAnswerPageVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                practicePaperAnswerMapper.studentPage(requestVM));
    }

    @Override
    public PracticePaperAnswerInfo calculateExamPaperAnswer(PracticePaperSubmitVM practicePaperSubmitVM, User user) {
        PracticePaperAnswerInfo practicePaperAnswerInfo = new PracticePaperAnswerInfo();
        Date now = new Date();
        Practice practice = practicePaperMapper.selectByPrimaryKey(practicePaperSubmitVM.getId());
        ExamPaperTypeEnum paperTypeEnum = ExamPaperTypeEnum.fromCode(practice.getPaperType());
        //任务试卷只能做一次
        if (paperTypeEnum == ExamPaperTypeEnum.Task) {
            PracticePaperAnswer practicePaperAnswer = practicePaperAnswerMapper.getByPidUid(practicePaperSubmitVM.getId(), user.getId());
            if (null != practicePaperAnswer)
                return null;
        }
        String frameTextContent = textContentService.selectById(practice.getFrameTextContentId()).getContent();
        List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JsonUtil.toJsonListObject(frameTextContent, ExamPaperTitleItemObject.class);
        List<Integer> questionIds = examPaperTitleItemObjects.stream().flatMap(t -> t.getQuestionItems().stream().map(q -> q.getId())).collect(Collectors.toList());
        List<Question> questions = questionMapper.selectByIds(questionIds);
        //将题目结构的转化为题目答案
        List<PracticePaperQuestionCustomerAnswer> practicePaperQuestionCustomerAnswers = examPaperTitleItemObjects.stream()
                .flatMap(t -> t.getQuestionItems().stream()
                        .map(q -> {
                            Question question = questions.stream().filter(tq -> tq.getId().equals(q.getId())).findFirst().get();
                            PracticePaperSubmitItemVM customerQuestionAnswer = practicePaperSubmitVM.getAnswerItems().stream()
                                    .filter(tq -> tq.getQuestionId().equals(q.getId()))
                                    .findFirst()
                                    .orElse(null);
                            return ExamPaperQuestionCustomerAnswerFromVM(question, customerQuestionAnswer, practice, q.getItemOrder(), user, now);
                        })
                ).collect(Collectors.toList());

        PracticePaperAnswer practicePaperAnswer = ExamPaperAnswerFromVM(practicePaperSubmitVM, practice, practicePaperQuestionCustomerAnswers, user, now);
        practicePaperAnswerInfo.setPractice(practice);
        practicePaperAnswerInfo.setPracticePaperAnswer(practicePaperAnswer);
        practicePaperAnswerInfo.setPracticePaperQuestionCustomerAnswers(practicePaperQuestionCustomerAnswers);
        return practicePaperAnswerInfo;
    }

    @Override
    @Transactional
    public String judge(PracticePaperSubmitVM practicePaperSubmitVM) {
        PracticePaperAnswer practicePaperAnswer = practicePaperAnswerMapper.selectByPrimaryKey(practicePaperSubmitVM.getId());
        List<PracticePaperSubmitItemVM> judgeItems = practicePaperSubmitVM.getAnswerItems().stream().filter(d -> d.getDoRight() == null).collect(Collectors.toList());
        List<ExamPaperAnswerUpdate> examPaperAnswerUpdates = new ArrayList<>(judgeItems.size());
        Integer customerScore = practicePaperAnswer.getUserScore();
        Integer questionCorrect = practicePaperAnswer.getQuestionCorrect();
        for (PracticePaperSubmitItemVM d : judgeItems) {
            ExamPaperAnswerUpdate examPaperAnswerUpdate = new ExamPaperAnswerUpdate();
            examPaperAnswerUpdate.setId(d.getId());
            examPaperAnswerUpdate.setCustomerScore(ExamUtil.scoreFromVM(d.getScore()));
            boolean doRight = examPaperAnswerUpdate.getCustomerScore().equals(ExamUtil.scoreFromVM(d.getQuestionScore()));
            examPaperAnswerUpdate.setDoRight(doRight);
            examPaperAnswerUpdates.add(examPaperAnswerUpdate);
            customerScore += examPaperAnswerUpdate.getCustomerScore();
            if (examPaperAnswerUpdate.getDoRight()) {
                ++questionCorrect;
            }
        }
        practicePaperAnswer.setUserScore(customerScore);
        practicePaperAnswer.setQuestionCorrect(questionCorrect);
        practicePaperAnswer.setStatus(ExamPaperAnswerStatusEnum.Complete.getCode());
        practicePaperAnswerMapper.updateByPrimaryKeySelective(practicePaperAnswer);
        practicePaperQuestionCustomerAnswerService.updateScore(examPaperAnswerUpdates);

        ExamPaperTypeEnum examPaperTypeEnum = ExamPaperTypeEnum.fromCode(practicePaperAnswer.getPaperType());
        switch (examPaperTypeEnum) {
            case Task:
                //任务试卷批改完成后，需要更新任务的状态
                Practice practice = practicePaperMapper.selectByPrimaryKey(practicePaperAnswer.getPracticePaperId());
                Integer taskId = practice.getTaskPracticeId();
                Integer userId = practicePaperAnswer.getCreateUser();
                TaskExamCustomerAnswer taskExamCustomerAnswer = taskExamCustomerAnswerMapper.getByTUid(taskId, userId);
                TextContent textContent = textContentService.selectById(taskExamCustomerAnswer.getTextContentId());
                List<TaskItemAnswerObject> taskItemAnswerObjects = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemAnswerObject.class);
                taskItemAnswerObjects.stream()
                        .filter(d -> d.getExamPaperAnswerId().equals(practicePaperAnswer.getId()))
                        .findFirst().ifPresent(taskItemAnswerObject -> taskItemAnswerObject.setStatus(practicePaperAnswer.getStatus()));
                textContentService.jsonConvertUpdate(textContent, taskItemAnswerObjects, null);
                textContentService.updateByIdFilter(textContent);
                break;
            default:
                break;
        }
        return ExamUtil.scoreToVM(customerScore);
    }

    @Override
    public PracticePaperSubmitVM examPaperAnswerToVM(Integer id) {
        PracticePaperSubmitVM practicePaperSubmitVM = new PracticePaperSubmitVM();
        PracticePaperAnswer practicePaperAnswer = practicePaperAnswerMapper.selectByPrimaryKey(id);
        practicePaperSubmitVM.setId(practicePaperAnswer.getId());
        practicePaperSubmitVM.setDoTime(practicePaperAnswer.getDoTime());
        practicePaperSubmitVM.setScore(ExamUtil.scoreToVM(practicePaperAnswer.getUserScore()));
        List<PracticePaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers = practicePaperQuestionCustomerAnswerService.selectListByPaperAnswerId(practicePaperAnswer.getId());
        List<PracticePaperSubmitItemVM> practicePaperSubmitItemVMS = examPaperQuestionCustomerAnswers.stream()
                .map(a -> practicePaperQuestionCustomerAnswerService.examPaperQuestionCustomerAnswerToVM(a))
                .collect(Collectors.toList());
        practicePaperSubmitVM.setAnswerItems(practicePaperSubmitItemVMS);
        return practicePaperSubmitVM;
    }

    @Override
    public Integer selectAllCount() {
        return practicePaperAnswerMapper.selectAllCount();
    }

    @Override
    public List<Integer> selectMothCount() {
        Date startTime = DateTimeUtil.getMonthStartDay();
        Date endTime = DateTimeUtil.getMonthEndDay();
        List<KeyValue> mouthCount = practicePaperAnswerMapper.selectCountByDate(startTime, endTime);
        List<String> mothStartToNowFormat = DateTimeUtil.MothStartToNowFormat();
        return mothStartToNowFormat.stream().map(md -> {
            KeyValue keyValue = mouthCount.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
            return null == keyValue ? 0 : keyValue.getValue();
        }).collect(Collectors.toList());
    }
    /**
     * 用户提交答案的转化存储对象
     *
     * @param question               question
     * @param customerQuestionAnswer customerQuestionAnswer
     * @param practice               practice
     * @param itemOrder              itemOrder
     * @param user                   user
     * @param now                    now
     * @return ExamPaperQuestionCustomerAnswer
     */
    private PracticePaperQuestionCustomerAnswer ExamPaperQuestionCustomerAnswerFromVM(Question question, PracticePaperSubmitItemVM customerQuestionAnswer, Practice practice, Integer itemOrder, User user, Date now) {
        PracticePaperQuestionCustomerAnswer practicePaperQuestionCustomerAnswer = new PracticePaperQuestionCustomerAnswer();
        practicePaperQuestionCustomerAnswer.setQuestionId(question.getId());
        practicePaperQuestionCustomerAnswer.setPracticePaperId(practice.getId());
        practicePaperQuestionCustomerAnswer.setQuestionScore(question.getScore());
        practicePaperQuestionCustomerAnswer.setRankLevel(practice.getRankLevel());
        practicePaperQuestionCustomerAnswer.setItemOrder(itemOrder);
        practicePaperQuestionCustomerAnswer.setCreateTime(now);
        practicePaperQuestionCustomerAnswer.setCreateUser(user.getId());
        practicePaperQuestionCustomerAnswer.setQuestionType(question.getQuestionType());
        practicePaperQuestionCustomerAnswer.setQuestionTextContentId(question.getInfoTextContentId());
        if (null == customerQuestionAnswer) {
            practicePaperQuestionCustomerAnswer.setCustomerScore(0);
        } else {
            setSpecialFromVM(practicePaperQuestionCustomerAnswer, question, customerQuestionAnswer);
        }
        return practicePaperQuestionCustomerAnswer;
    }

    /**
     * 判断提交答案是否正确，保留用户提交的答案
     *
     * @param practicePaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer
     * @param question                        question
     * @param customerQuestionAnswer          customerQuestionAnswer
     */
    private void setSpecialFromVM(PracticePaperQuestionCustomerAnswer practicePaperQuestionCustomerAnswer, Question question, PracticePaperSubmitItemVM customerQuestionAnswer) {
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(practicePaperQuestionCustomerAnswer.getQuestionType());
        switch (questionTypeEnum) {
            case SingleChoice:
            case TrueFalse:
                practicePaperQuestionCustomerAnswer.setAnswer(customerQuestionAnswer.getContent());
                practicePaperQuestionCustomerAnswer.setDoRight(question.getCorrect().equals(customerQuestionAnswer.getContent()));
                practicePaperQuestionCustomerAnswer.setCustomerScore(practicePaperQuestionCustomerAnswer.getDoRight() ? question.getScore() : 0);
                break;
            case MultipleChoice:
                String customerAnswer = ExamUtil.contentToString(customerQuestionAnswer.getContentArray());
                practicePaperQuestionCustomerAnswer.setAnswer(customerAnswer);
                practicePaperQuestionCustomerAnswer.setDoRight(customerAnswer.equals(question.getCorrect()));
                practicePaperQuestionCustomerAnswer.setCustomerScore(practicePaperQuestionCustomerAnswer.getDoRight() ? question.getScore() : 0);
                break;
            case GapFilling:
                String correctAnswer = JsonUtil.toJsonStr(customerQuestionAnswer.getContentArray());
                practicePaperQuestionCustomerAnswer.setAnswer(correctAnswer);
                practicePaperQuestionCustomerAnswer.setCustomerScore(0);
                break;
            default:
                practicePaperQuestionCustomerAnswer.setAnswer(customerQuestionAnswer.getContent());
                practicePaperQuestionCustomerAnswer.setCustomerScore(0);
                break;
        }
    }

    private PracticePaperAnswer ExamPaperAnswerFromVM(PracticePaperSubmitVM examPaperSubmitVM, Practice practice, List<PracticePaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers, User user, Date now) {
        Integer systemScore = examPaperQuestionCustomerAnswers.stream().mapToInt(a -> a.getCustomerScore()).sum();
        long questionCorrect = examPaperQuestionCustomerAnswers.stream().filter(a -> a.getCustomerScore().equals(a.getQuestionScore())).count();
        PracticePaperAnswer practicePaperAnswer = new PracticePaperAnswer();
        practicePaperAnswer.setPaperName(practice.getName());
        practicePaperAnswer.setDoTime(examPaperSubmitVM.getDoTime());
        practicePaperAnswer.setPracticePaperId(practice.getId());
        practicePaperAnswer.setCreateUser(user.getId());
        practicePaperAnswer.setCreateTime(now);
        practicePaperAnswer.setRankLevel(practice.getRankLevel());
        practicePaperAnswer.setQuestionCount(practice.getQuestionCount());
        practicePaperAnswer.setPaperType(practice.getPaperType());
        practicePaperAnswer.setSystemScore(systemScore);
        practicePaperAnswer.setUserScore(systemScore);
        practicePaperAnswer.setTaskPracticeId(practice.getTaskPracticeId());
        practicePaperAnswer.setQuestionCorrect((int) questionCorrect);
        boolean needJudge = examPaperQuestionCustomerAnswers.stream().anyMatch(d -> QuestionTypeEnum.needSaveTextContent(d.getQuestionType()));
        if (needJudge) {
            practicePaperAnswer.setStatus(ExamPaperAnswerStatusEnum.WaitJudge.getCode());
        } else {
            practicePaperAnswer.setStatus(ExamPaperAnswerStatusEnum.Complete.getCode());
        }
        return practicePaperAnswer;
    }
    @Override
    public PageInfo<PracticePaperAnswer> adminPage(PracticePaperAnswerPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                practicePaperAnswerMapper.adminPage(requestVM));
    }
}
