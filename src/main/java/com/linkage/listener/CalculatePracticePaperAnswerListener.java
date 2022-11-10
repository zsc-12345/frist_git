package com.linkage.listener;

import com.linkage.domain.*;
import com.linkage.domain.enums.ExamPaperTypeEnum;
import com.linkage.domain.enums.QuestionTypeEnum;
import com.linkage.event.CalculatePracticePaperAnswerCompleteEvent;
import com.linkage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * @version 3.5.0
 * @description:  The type Calculate Practice paper answer listener.
 * Copyright (C), 2020-2021, 武汉思维跳跃科技有限公司
 * @date 2021/12/25 9:45
 */
@Component
public class CalculatePracticePaperAnswerListener implements ApplicationListener<CalculatePracticePaperAnswerCompleteEvent> {

    private final PracticePaperAnswerService practicePaperAnswerService;
    private final PracticePaperQuestionCustomerAnswerService practicePaperQuestionCustomerAnswerService;
    private final TextContentService textContentService;
    private final TaskPracticeCustomerAnswerService practiceCustomerAnswerService;

    /**
     * Instantiates a new Calculate Practice paper answer listener.
     *
     * @param practicePaperAnswerService                 the Practice paper answer service
     * @param practicePaperQuestionCustomerAnswerService the Practice paper question customer answer service
     * @param textContentService                     the text content service
     * @param practiceCustomerAnswerService              the Practice customer answer service
     */
    @Autowired
    public CalculatePracticePaperAnswerListener(PracticePaperAnswerService practicePaperAnswerService, PracticePaperQuestionCustomerAnswerService practicePaperQuestionCustomerAnswerService, TextContentService textContentService, TaskPracticeCustomerAnswerService practiceCustomerAnswerService) {
        this.practicePaperAnswerService = practicePaperAnswerService;
        this.practicePaperQuestionCustomerAnswerService = practicePaperQuestionCustomerAnswerService;
        this.textContentService = textContentService;
        this.practiceCustomerAnswerService = practiceCustomerAnswerService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(CalculatePracticePaperAnswerCompleteEvent calculatePracticePaperAnswerCompleteEvent) {
        Date now = new Date();

        PracticePaperAnswerInfo practicePaperAnswerInfo = (PracticePaperAnswerInfo) calculatePracticePaperAnswerCompleteEvent.getSource();
        Practice practicePaper = practicePaperAnswerInfo.getPractice();
        PracticePaperAnswer practicePaperAnswer = practicePaperAnswerInfo.getPracticePaperAnswer();
        List<PracticePaperQuestionCustomerAnswer> practicePaperQuestionCustomerAnswer = practicePaperAnswerInfo.getPracticePaperQuestionCustomerAnswers();

        practicePaperAnswerService.insertByFilter(practicePaperAnswer);
        practicePaperQuestionCustomerAnswer.stream().filter(a -> QuestionTypeEnum.needSaveTextContent(a.getQuestionType())).forEach(d -> {
            TextContent textContent = new TextContent(d.getAnswer(), now);
            textContentService.insertByFilter(textContent);
            d.setTextContentId(textContent.getId());
            d.setAnswer(null);
        });
        practicePaperQuestionCustomerAnswer.forEach(d -> {
            d.setPracticePaperAnswerId(practicePaperAnswer.getId());
        });
        practicePaperQuestionCustomerAnswerService.insertList(practicePaperQuestionCustomerAnswer);

        switch (ExamPaperTypeEnum.fromCode(practicePaper.getPaperType())) {
            case Task: {
                practiceCustomerAnswerService.insertOrUpdate(practicePaper, practicePaperAnswer, now);
                break;
            }
            default:
                break;
        }
    }
}
