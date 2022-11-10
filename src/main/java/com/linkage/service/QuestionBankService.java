package com.linkage.service;

import com.github.pagehelper.PageInfo;
import com.linkage.domain.Question;
import com.linkage.domain.QuestionBank;
import com.linkage.viewmodel.admin.question.QuestionEditRequestVM;
import com.linkage.viewmodel.admin.question.QuestionPageRequestVM;
import com.linkage.viewmodel.admin.questionbank.QuestionBankEditRequestVM;
import com.linkage.viewmodel.admin.questionbank.QuestionBankPageRequestVM;

import java.util.List;

public interface QuestionBankService extends BaseService<QuestionBank> {

    PageInfo<QuestionBank> page(QuestionBankPageRequestVM requestVM);

    QuestionBank insertFullQuestion(QuestionBankEditRequestVM model, Integer userId);

    QuestionBank updateFullQuestion(QuestionBankEditRequestVM model);

    QuestionBankEditRequestVM getQuestionEditRequestVM(Integer questionId);

    QuestionBankEditRequestVM getQuestionEditRequestVM(QuestionBank questionBank);

    Integer selectAllCount();

    List<Integer> selectMothCount();
}
