package com.linkage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linkage.domain.*;
import com.linkage.domain.enums.ExamPaperTypeEnum;
import com.linkage.domain.exam.ExamPaperQuestionItemObject;
import com.linkage.domain.exam.ExamPaperTitleItemObject;
import com.linkage.domain.other.KeyValue;
import com.linkage.repository.PracticeMapper;
import com.linkage.repository.QuestionMapper;
import com.linkage.service.PracticeService;
import com.linkage.service.QuestionService;
import com.linkage.service.SubjectService;
import com.linkage.service.TextContentService;
import com.linkage.service.enums.ActionEnum;
import com.linkage.utility.DateTimeUtil;
import com.linkage.utility.JsonUtil;
import com.linkage.utility.ModelMapperSingle;
import com.linkage.viewmodel.admin.exam.ExamPaperTitleItemVM;
import com.linkage.viewmodel.admin.practice.PracticePaperEditRequestVM;
import com.linkage.viewmodel.admin.practice.PracticePaperPageRequestVM;
import com.linkage.viewmodel.admin.question.QuestionEditRequestVM;
import com.linkage.viewmodel.student.Practice.PracticePageVM;
import com.linkage.viewmodel.student.dashboard.PracticeFilter;
import com.linkage.viewmodel.student.dashboard.PracticeInfo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class PracticeServiceImpl extends BaseServiceImpl<Practice> implements PracticeService {

    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();

    private final PracticeMapper practiceMapper;
    private final QuestionMapper questionMapper;
    private final TextContentService textContentService;
    private final QuestionService questionService;
    private final SubjectService subjectService;

    @Autowired
    public PracticeServiceImpl(PracticeMapper practiceMapper, QuestionMapper questionMapper, TextContentService textContentService, QuestionService questionService, SubjectService subjectService) {
        super(practiceMapper);
        this.practiceMapper = practiceMapper;
        this.questionMapper = questionMapper;
        this.textContentService = textContentService;
        this.questionService = questionService;
        this.subjectService = subjectService;
    }


    @Override
    public PageInfo<Practice> page(PracticePaperPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                practiceMapper.page(requestVM));
    }

    @Override
    public PageInfo<Practice> taskExamPage(PracticePaperPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                practiceMapper.taskExamPage(requestVM));
    }

    @Override
    public PageInfo<Practice> studentPage(PracticePageVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                practiceMapper.studentPage(requestVM));
    }

    @Override
    @Transactional
    public Practice savePaperFromVM(PracticePaperEditRequestVM practicePaperEditRequestVM, User user) {
        ActionEnum actionEnum = (practicePaperEditRequestVM.getId() == null) ? ActionEnum.ADD : ActionEnum.UPDATE;
        Date now = new Date();
        List<ExamPaperTitleItemVM> titleItemsVM = practicePaperEditRequestVM.getTitleItems();
        List<ExamPaperTitleItemObject> frameTextContentList = frameTextContentFromVM(titleItemsVM);
        String frameTextContentStr = JsonUtil.toJsonStr(frameTextContentList);

        Practice practice;
        if (actionEnum == ActionEnum.ADD) {
            practice = modelMapper.map(practicePaperEditRequestVM, Practice.class);
            TextContent frameTextContent = new TextContent(frameTextContentStr, now);
            textContentService.insertByFilter(frameTextContent);
            practice.setFrameTextContentId(frameTextContent.getId());
            practice.setCreateTime(now);
            practice.setCreateUser(user.getId());
            practice.setDeleted(false);
            examPaperFromVM(practicePaperEditRequestVM, practice, titleItemsVM);
            practiceMapper.insertSelective(practice);
        } else {
            practice = practiceMapper.selectByPrimaryKey(practicePaperEditRequestVM.getId());
            TextContent frameTextContent = textContentService.selectById(practice.getFrameTextContentId());
            frameTextContent.setContent(frameTextContentStr);
            textContentService.updateByIdFilter(frameTextContent);
            modelMapper.map(practicePaperEditRequestVM, practice);
            examPaperFromVM(practicePaperEditRequestVM, practice, titleItemsVM);
            practiceMapper.updateByPrimaryKeySelective(practice);
        }
        return practice;
    }

    @Override
    public PracticePaperEditRequestVM examPaperToVM(Integer id) {
        Practice practice = practiceMapper.selectByPrimaryKey(id);
        PracticePaperEditRequestVM vm = modelMapper.map(practice, PracticePaperEditRequestVM.class);
        vm.setTechnicalTypes(practice.getTechnicalTypes());
        TextContent frameTextContent = textContentService.selectById(practice.getFrameTextContentId());
        List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JsonUtil.toJsonListObject(frameTextContent.getContent(), ExamPaperTitleItemObject.class);
        List<Integer> questionIds = examPaperTitleItemObjects.stream()
                .flatMap(t -> t.getQuestionItems().stream()
                        .map(q -> q.getId()))
                .collect(Collectors.toList());
        List<Question> questions = questionMapper.selectByIds(questionIds);
        List<ExamPaperTitleItemVM> examPaperTitleItemVMS = examPaperTitleItemObjects.stream().map(t -> {
            ExamPaperTitleItemVM tTitleVM = modelMapper.map(t, ExamPaperTitleItemVM.class);
            List<QuestionEditRequestVM> questionItemsVM = t.getQuestionItems().stream().map(i -> {
                Question question = questions.stream().filter(q -> q.getId().equals(i.getId())).findFirst().get();
                QuestionEditRequestVM questionEditRequestVM = questionService.getQuestionEditRequestVM(question);
                questionEditRequestVM.setItemOrder(i.getItemOrder());
                return questionEditRequestVM;
            }).collect(Collectors.toList());
            tTitleVM.setQuestionItems(questionItemsVM);
            return tTitleVM;
        }).collect(Collectors.toList());
        vm.setTitleItems(examPaperTitleItemVMS);
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(practice.getPaperType())) {
            List<String> limitDateTime = Arrays.asList(DateTimeUtil.dateFormat(practice.getLimitStartTime()), DateTimeUtil.dateFormat(practice.getLimitEndTime()));
            vm.setLimitDateTime(limitDateTime);
        }
        return vm;
    }

    @Override
    public List<PracticeInfo> indexPaper(PracticeFilter practiceFilter) {
        return practiceMapper.indexPaper(practiceFilter);
    }

    @Override
    public Integer selectAllCount() {
        return practiceMapper.selectAllCount();
    }

    @Override
    public List<Integer> selectMothCount() {
        Date startTime = DateTimeUtil.getMonthStartDay();
        Date endTime = DateTimeUtil.getMonthEndDay();
        List<KeyValue> mouthCount = practiceMapper.selectCountByDate(startTime, endTime);
        List<String> mothStartToNowFormat = DateTimeUtil.MothStartToNowFormat();
        return mothStartToNowFormat.stream().map(md -> {
            KeyValue keyValue = mouthCount.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
            return null == keyValue ? 0 : keyValue.getValue();
        }).collect(Collectors.toList());
    }
    private void examPaperFromVM(PracticePaperEditRequestVM PracticePaperEditRequestVM, Practice practice, List<ExamPaperTitleItemVM> titleItemsVM) {
        Integer technicalTypes = subjectService.levelBySubjectId(PracticePaperEditRequestVM.getRankLevel());
        Integer questionCount = titleItemsVM.stream()
                .mapToInt(t -> t.getQuestionItems().size()).sum();
        practice.setQuestionCount(questionCount);
        practice.setTechnicalTypes(technicalTypes);
        List<String> dateTimes = PracticePaperEditRequestVM.getLimitDateTime();
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(practice.getPaperType())) {
            practice.setLimitStartTime(DateTimeUtil.parse(dateTimes.get(0), DateTimeUtil.STANDER_FORMAT));
            practice.setLimitEndTime(DateTimeUtil.parse(dateTimes.get(1), DateTimeUtil.STANDER_FORMAT));
        }
    }

    private List<ExamPaperTitleItemObject> frameTextContentFromVM(List<ExamPaperTitleItemVM> titleItems) {
        AtomicInteger index = new AtomicInteger(1);
        return titleItems.stream().map(t -> {
            ExamPaperTitleItemObject titleItem = modelMapper.map(t, ExamPaperTitleItemObject.class);
            List<ExamPaperQuestionItemObject> questionItems = t.getQuestionItems().stream()
                    .map(q -> {
                        ExamPaperQuestionItemObject examPaperQuestionItemObject = modelMapper.map(q, ExamPaperQuestionItemObject.class);
                        examPaperQuestionItemObject.setItemOrder(index.getAndIncrement());
                        return examPaperQuestionItemObject;
                    })
                    .collect(Collectors.toList());
            titleItem.setQuestionItems(questionItems);
            return titleItem;
        }).collect(Collectors.toList());
    }
}
