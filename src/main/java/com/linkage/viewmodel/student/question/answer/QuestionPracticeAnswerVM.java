package com.linkage.viewmodel.student.question.answer;

import com.linkage.viewmodel.admin.question.QuestionEditRequestVM;
import com.linkage.viewmodel.student.Practice.PracticePaperSubmitItemVM;

public class QuestionPracticeAnswerVM {
    private QuestionEditRequestVM questionVM;
    private PracticePaperSubmitItemVM questionAnswerVM;

    public QuestionEditRequestVM getQuestionVM() {
        return questionVM;
    }

    public void setQuestionVM(QuestionEditRequestVM questionVM) {
        this.questionVM = questionVM;
    }

    public PracticePaperSubmitItemVM getQuestionAnswerVM() {
        return questionAnswerVM;
    }

    public void setQuestionAnswerVM(PracticePaperSubmitItemVM questionAnswerVM) {
        this.questionAnswerVM = questionAnswerVM;
    }
}
