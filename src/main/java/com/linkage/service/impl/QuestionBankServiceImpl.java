package com.linkage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linkage.domain.Question;
import com.linkage.domain.QuestionBank;
import com.linkage.domain.TextContent;
import com.linkage.domain.enums.QuestionBankStatusEnum;
import com.linkage.domain.enums.QuestionBankTypeEnum;
import com.linkage.domain.enums.QuestionStatusEnum;
import com.linkage.domain.enums.QuestionTypeEnum;
import com.linkage.domain.other.KeyValue;
import com.linkage.domain.question.QuestionItemObject;
import com.linkage.domain.question.QuestionObject;
import com.linkage.domain.questionbank.QuestionBankItemObject;
import com.linkage.domain.questionbank.QuestionBankObject;
import com.linkage.repository.QuestionBankMapper;
import com.linkage.repository.QuestionMapper;
import com.linkage.service.QuestionBankService;
import com.linkage.service.QuestionService;
import com.linkage.service.SubjectService;
import com.linkage.service.TextContentService;
import com.linkage.utility.DateTimeUtil;
import com.linkage.utility.ExamUtil;
import com.linkage.utility.JsonUtil;
import com.linkage.utility.ModelMapperSingle;
import com.linkage.viewmodel.admin.question.QuestionEditItemVM;
import com.linkage.viewmodel.admin.question.QuestionEditRequestVM;
import com.linkage.viewmodel.admin.question.QuestionPageRequestVM;
import com.linkage.viewmodel.admin.questionbank.QuestionBankEditItemVM;
import com.linkage.viewmodel.admin.questionbank.QuestionBankEditRequestVM;
import com.linkage.viewmodel.admin.questionbank.QuestionBankPageRequestVM;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionBankServiceImpl extends BaseServiceImpl<QuestionBank> implements QuestionBankService {

    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();
    private final QuestionBankMapper questionBankMapper;
    private final TextContentService textContentService;
    private final SubjectService subjectService;

    @Autowired
    public QuestionBankServiceImpl(QuestionBankMapper questionBankMapper, TextContentService textContentService, SubjectService subjectService) {
        super(questionBankMapper);
        this.textContentService = textContentService;
        this.questionBankMapper = questionBankMapper;
        this.subjectService = subjectService;
    }

    @Override
    public PageInfo<QuestionBank> page(QuestionBankPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                questionBankMapper.page(requestVM)
        );
    }


    @Override
    @Transactional
    public QuestionBank insertFullQuestion(QuestionBankEditRequestVM model, Integer userId) {
        Date now = new Date();
        Integer gradeLevel = subjectService.levelBySubjectId(model.getSubjectId());

        //题干、解析、选项等 插入
        TextContent infoTextContent = new TextContent();
        infoTextContent.setCreateTime(now);
        setQuestionBankInfoFromVM(infoTextContent, model);
        textContentService.insertByFilter(infoTextContent);

        QuestionBank questionBank = new QuestionBank();
        questionBank.setSubjectId(model.getSubjectId());
        questionBank.setGradeLevel(gradeLevel);
        questionBank.setCreateTime(now);
        questionBank.setQuestionType(model.getQuestionType());
        questionBank.setStatus(QuestionBankStatusEnum.OK.getCode());
        questionBank.setCorrectFromVM(model.getCorrect(), model.getCorrectArray());
        questionBank.setScore(ExamUtil.scoreFromVM(model.getScore()));
        questionBank.setInfoTextContentId(infoTextContent.getId());
        questionBank.setCreateUser(userId);
        questionBank.setDeleted(false);
        questionBankMapper.insertSelective(questionBank);
        return questionBank;
    }

    @Override
    @Transactional
    public QuestionBank updateFullQuestion(QuestionBankEditRequestVM model) {
        Integer gradeLevel = subjectService.levelBySubjectId(model.getSubjectId());
        QuestionBank questionBank = questionBankMapper.selectByPrimaryKey(model.getId());
        questionBank.setSubjectId(model.getSubjectId());
        questionBank.setGradeLevel(gradeLevel);
        questionBank.setScore(ExamUtil.scoreFromVM(model.getScore()));
        questionBank.setCorrectFromVM(model.getCorrect(), model.getCorrectArray());
        questionBankMapper.updateByPrimaryKeySelective(questionBank);

        //题干、解析、选项等 更新
        TextContent infoTextContent = textContentService.selectById(questionBank.getInfoTextContentId());
        setQuestionBankInfoFromVM(infoTextContent, model);
        textContentService.updateByIdFilter(infoTextContent);

        return questionBank;
    }

    @Override
    public QuestionBankEditRequestVM getQuestionEditRequestVM(Integer questionId) {
        //题目映射
        QuestionBank questionBank = questionBankMapper.selectByPrimaryKey(questionId);
        return getQuestionEditRequestVM(questionBank);
    }

    @Override
    public QuestionBankEditRequestVM getQuestionEditRequestVM(QuestionBank questionBank) {
        //题目映射
        TextContent questionInfoTextContent = textContentService.selectById(questionBank.getInfoTextContentId());
        QuestionBankObject questionBankObject = JsonUtil.toJsonObject(questionInfoTextContent.getContent(), QuestionBankObject.class);
        QuestionBankEditRequestVM questionBankEditRequestVM = modelMapper.map(questionBank, QuestionBankEditRequestVM.class);
        questionBankEditRequestVM.setTitle(questionBankObject.getTitleContent());

        //答案
        QuestionBankTypeEnum questionBankTypeEnum = QuestionBankTypeEnum.fromCode(questionBank.getQuestionType());
        switch (questionBankTypeEnum) {
            case SingleChoice:
            case TrueFalse:
                questionBankEditRequestVM.setCorrect(questionBank.getCorrect());
                break;
            case MultipleChoice:
                questionBankEditRequestVM.setCorrectArray(ExamUtil.contentToArray(questionBank.getCorrect()));
                break;
            case GapFilling:
                List<String> correctContent = questionBankObject.getQuestionItemObjects().stream().map(d -> d.getContent()).collect(Collectors.toList());
                questionBankEditRequestVM.setCorrectArray(correctContent);
                break;
            case ShortAnswer:
                questionBankEditRequestVM.setCorrect(questionBankObject.getCorrect());
                break;
            default:
                break;
        }
        questionBankEditRequestVM.setScore(ExamUtil.scoreToVM(questionBank.getScore()));
        questionBankEditRequestVM.setAnalyze(questionBankObject.getAnalyze());


        //题目项映射
        List<QuestionBankEditItemVM> editItems = questionBankObject.getQuestionItemObjects().stream().map(o -> {
            QuestionBankEditItemVM questionBankEditItemVM = modelMapper.map(o, QuestionBankEditItemVM.class);
           if (o.getScore() != null) {
               questionBankEditItemVM.setScore(ExamUtil.scoreToVM(o.getScore()));
           }
            return questionBankEditItemVM;
        }).collect(Collectors.toList());
        questionBankEditRequestVM.setItems(editItems);
        return questionBankEditRequestVM;
    }

    public void setQuestionBankInfoFromVM(TextContent infoTextContent, QuestionBankEditRequestVM model) {
        List<QuestionBankItemObject> itemObjects = model.getItems().stream().map(i ->
                {
                    QuestionBankItemObject item = new QuestionBankItemObject();
                    item.setPrefix(i.getPrefix());
                    item.setContent(i.getContent());
                    item.setItemUuid(i.getItemUuid());
                    item.setScore(ExamUtil.scoreFromVM(i.getScore()));
                    return item;
                }
        ).collect(Collectors.toList());
        QuestionBankObject questionBankObject = new QuestionBankObject();
        questionBankObject.setQuestionItemObjects(itemObjects);
        questionBankObject.setAnalyze(model.getAnalyze());
        questionBankObject.setTitleContent(model.getTitle());
        questionBankObject.setCorrect(model.getCorrect());
        infoTextContent.setContent(JsonUtil.toJsonStr(questionBankObject));
    }

    @Override
    public Integer selectAllCount() {
        return questionBankMapper.selectAllCount();
    }

    @Override
    public List<Integer> selectMothCount() {
        Date startTime = DateTimeUtil.getMonthStartDay();
        Date endTime = DateTimeUtil.getMonthEndDay();
        List<String> mothStartToNowFormat = DateTimeUtil.MothStartToNowFormat();
        List<KeyValue> mouthCount = questionBankMapper.selectCountByDate(startTime, endTime);
        return mothStartToNowFormat.stream().map(md -> {
            KeyValue keyValue = mouthCount.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
            return null == keyValue ? 0 : keyValue.getValue();
        }).collect(Collectors.toList());
    }


}
