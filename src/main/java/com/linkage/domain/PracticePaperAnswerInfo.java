package com.linkage.domain;

import java.util.List;

public class PracticePaperAnswerInfo {

    public Practice practice;
    public PracticePaperAnswer practicePaperAnswer;
    public List<PracticePaperQuestionCustomerAnswer> practicePaperQuestionCustomerAnswers;

    public Practice getPractice() {
        return practice;
    }

    public void setPractice(Practice practice) {
        this.practice = practice;
    }

    public PracticePaperAnswer getPracticePaperAnswer() {
        return practicePaperAnswer;
    }

    public void setPracticePaperAnswer(PracticePaperAnswer practicePaperAnswer) {
        this.practicePaperAnswer = practicePaperAnswer;
    }

    public List<PracticePaperQuestionCustomerAnswer> getPracticePaperQuestionCustomerAnswers() {
        return practicePaperQuestionCustomerAnswers;
    }

    public void setPracticePaperQuestionCustomerAnswers(List<PracticePaperQuestionCustomerAnswer> practicePaperQuestionCustomerAnswers) {
        this.practicePaperQuestionCustomerAnswers = practicePaperQuestionCustomerAnswers;
    }
}
