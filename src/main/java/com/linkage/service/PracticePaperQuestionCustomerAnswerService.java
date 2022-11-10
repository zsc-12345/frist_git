package com.linkage.service;

import com.github.pagehelper.PageInfo;
import com.linkage.domain.PracticePaperQuestionCustomerAnswer;
import com.linkage.domain.other.ExamPaperAnswerUpdate;
import com.linkage.viewmodel.student.Practice.PracticePaperSubmitItemVM;
import com.linkage.viewmodel.student.question.answer.QuestionPagePracticeStudentRequestVM;
import com.linkage.viewmodel.student.question.answer.QuestionPageStudentRequestVM;

import java.util.List;

public interface PracticePaperQuestionCustomerAnswerService extends BaseService<PracticePaperQuestionCustomerAnswer>{

    PageInfo<PracticePaperQuestionCustomerAnswer> studentPage(QuestionPagePracticeStudentRequestVM requestVM);

    List<PracticePaperQuestionCustomerAnswer> selectListByPaperAnswerId(Integer id);

    /**
     * 试卷提交答案入库
     *
     * @param practicePaperQuestionCustomerAnswer List<practicePaperQuestionCustomerAnswer>
     */
    void insertList(List<PracticePaperQuestionCustomerAnswer> practicePaperQuestionCustomerAnswer);

    /**
     * 试卷问题答题信息转成ViewModel 传给前台
     *
     * @param qa PracticePaperQuestionCustomerAnswer
     * @return ExamPaperSubmitItemVM
     */
    PracticePaperSubmitItemVM examPaperQuestionCustomerAnswerToVM(PracticePaperQuestionCustomerAnswer qa);


    Integer selectAllCount();

    List<Integer> selectMothCount();

    int updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates);
}
