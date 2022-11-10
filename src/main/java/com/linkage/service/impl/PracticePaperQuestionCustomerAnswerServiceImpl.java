package com.linkage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linkage.domain.PracticePaperQuestionCustomerAnswer;
import com.linkage.domain.TextContent;
import com.linkage.domain.enums.QuestionTypeEnum;
import com.linkage.domain.other.ExamPaperAnswerUpdate;
import com.linkage.domain.other.KeyValue;
import com.linkage.repository.PracticePaperQuestionCustomerAnswerMapper;
import com.linkage.service.PracticePaperQuestionCustomerAnswerService;
import com.linkage.service.TextContentService;
import com.linkage.utility.DateTimeUtil;
import com.linkage.utility.ExamUtil;
import com.linkage.utility.JsonUtil;
import com.linkage.viewmodel.student.Practice.PracticePaperSubmitItemVM;
import com.linkage.viewmodel.student.question.answer.QuestionPagePracticeStudentRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PracticePaperQuestionCustomerAnswerServiceImpl extends BaseServiceImpl<PracticePaperQuestionCustomerAnswer> implements PracticePaperQuestionCustomerAnswerService {
    private final PracticePaperQuestionCustomerAnswerMapper practicePaperQuestionCustomerAnswerMapper;
    private final TextContentService textContentService;

    @Autowired
    public PracticePaperQuestionCustomerAnswerServiceImpl(PracticePaperQuestionCustomerAnswerMapper practicePaperQuestionCustomerAnswerMapper, TextContentService textContentService) {
        super(practicePaperQuestionCustomerAnswerMapper);
        this.practicePaperQuestionCustomerAnswerMapper = practicePaperQuestionCustomerAnswerMapper;
        this.textContentService = textContentService;
    }


    @Override
    public PageInfo<PracticePaperQuestionCustomerAnswer> studentPage(QuestionPagePracticeStudentRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                practicePaperQuestionCustomerAnswerMapper.studentPage(requestVM)
        );
    }

    @Override
    public List<PracticePaperQuestionCustomerAnswer> selectListByPaperAnswerId(Integer id) {
        return practicePaperQuestionCustomerAnswerMapper.selectListByPaperAnswerId(id);
    }


    @Override
    public void insertList(List<PracticePaperQuestionCustomerAnswer> practicePaperQuestionCustomerAnswers) {
        practicePaperQuestionCustomerAnswerMapper.insertList(practicePaperQuestionCustomerAnswers);
    }

    @Override
    public PracticePaperSubmitItemVM examPaperQuestionCustomerAnswerToVM(PracticePaperQuestionCustomerAnswer qa) {
        PracticePaperSubmitItemVM practicePaperSubmitItemVM = new PracticePaperSubmitItemVM();
        practicePaperSubmitItemVM.setId(qa.getId());
        practicePaperSubmitItemVM.setQuestionId(qa.getQuestionId());
        practicePaperSubmitItemVM.setDoRight(qa.getDoRight());
        practicePaperSubmitItemVM.setItemOrder(qa.getItemOrder());
        practicePaperSubmitItemVM.setQuestionScore(ExamUtil.scoreToVM(qa.getQuestionScore()));
        practicePaperSubmitItemVM.setScore(ExamUtil.scoreToVM(qa.getCustomerScore()));
        setSpecialToVM(practicePaperSubmitItemVM, qa);
        return practicePaperSubmitItemVM;
    }

    @Override
    public Integer selectAllCount() {
        return practicePaperQuestionCustomerAnswerMapper.selectAllCount();
    }

    @Override
    public List<Integer> selectMothCount() {
        Date startTime = DateTimeUtil.getMonthStartDay();
        Date endTime = DateTimeUtil.getMonthEndDay();
        List<KeyValue> mouthCount = practicePaperQuestionCustomerAnswerMapper.selectCountByDate(startTime, endTime);
        List<String> mothStartToNowFormat = DateTimeUtil.MothStartToNowFormat();
        return mothStartToNowFormat.stream().map(md -> {
            KeyValue keyValue = mouthCount.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
            return null == keyValue ? 0 : keyValue.getValue();
        }).collect(Collectors.toList());
    }

    @Override
    public int updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates) {
        return practicePaperQuestionCustomerAnswerMapper.updateScore(examPaperAnswerUpdates);
    }

    private void setSpecialToVM(PracticePaperSubmitItemVM practicePaperSubmitItemVM, PracticePaperQuestionCustomerAnswer practicePaperQuestionCustomerAnswer) {
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(practicePaperQuestionCustomerAnswer.getQuestionType());
        switch (questionTypeEnum) {
            case MultipleChoice:
                practicePaperSubmitItemVM.setContent(practicePaperQuestionCustomerAnswer.getAnswer());
                practicePaperSubmitItemVM.setContentArray(ExamUtil.contentToArray(practicePaperQuestionCustomerAnswer.getAnswer()));
                break;
            case GapFilling:
                TextContent textContent = textContentService.selectById(practicePaperQuestionCustomerAnswer.getTextContentId());
                List<String> correctAnswer = JsonUtil.toJsonListObject(textContent.getContent(), String.class);
                practicePaperSubmitItemVM.setContentArray(correctAnswer);
                break;
            default:
                if (QuestionTypeEnum.needSaveTextContent(practicePaperQuestionCustomerAnswer.getQuestionType())) {
                    TextContent content = textContentService.selectById(practicePaperQuestionCustomerAnswer.getTextContentId());
                    practicePaperSubmitItemVM.setContent(content.getContent());
                } else {
                    practicePaperSubmitItemVM.setContent(practicePaperQuestionCustomerAnswer.getAnswer());
                }
                break;
        }
    }
}
