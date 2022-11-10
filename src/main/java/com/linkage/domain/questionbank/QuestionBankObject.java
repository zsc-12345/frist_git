package com.linkage.domain.questionbank;



import java.util.List;

public class QuestionBankObject {

    private String titleContent;

    private String analyze;

    private List<QuestionBankItemObject> questionItemObjects;

    private String correct;

    public String getTitleContent() {
        return titleContent;
    }

    public void setTitleContent(String titleContent) {
        this.titleContent = titleContent;
    }

    public String getAnalyze() {
        return analyze;
    }

    public void setAnalyze(String analyze) {
        this.analyze = analyze;
    }

    public List<QuestionBankItemObject> getQuestionItemObjects() {
        return questionItemObjects;
    }

    public void setQuestionItemObjects(List<QuestionBankItemObject> questionItemObjects) {
        this.questionItemObjects = questionItemObjects;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }
}
