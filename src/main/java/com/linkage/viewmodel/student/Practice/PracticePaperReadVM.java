package com.linkage.viewmodel.student.Practice;

import com.linkage.viewmodel.admin.practice.PracticePaperEditRequestVM;
import com.linkage.viewmodel.student.exam.ExamPaperSubmitVM;

public class PracticePaperReadVM {
    private PracticePaperEditRequestVM paper;
    private PracticePaperSubmitVM answer;

    public PracticePaperEditRequestVM getPaper() {
        return paper;
    }

    public void setPaper(PracticePaperEditRequestVM paper) {
        this.paper = paper;
    }

    public PracticePaperSubmitVM getAnswer() {
        return answer;
    }

    public void setAnswer(PracticePaperSubmitVM answer) {
        this.answer = answer;
    }
}
