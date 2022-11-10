package com.linkage.viewmodel.admin.questionbank;


import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


public class QuestionBankEditRequestVM {

    private Integer id;
    @NotNull
    private Integer questionType;
    @NotNull
    private Integer subjectId;
    @NotBlank
    private String title;

    private Integer gradeLevel;

    @Valid
    private List<QuestionBankEditItemVM> items;
    @NotBlank
    private String analyze;

    private List<String> correctArray;

    private String correct;
    @NotBlank
    private String score;

    private Integer itemOrder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(Integer gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public List<QuestionBankEditItemVM> getItems() {
        return items;
    }

    public void setItems(List<QuestionBankEditItemVM> items) {
        this.items = items;
    }

    public String getAnalyze() {
        return analyze;
    }

    public void setAnalyze(String analyze) {
        this.analyze = analyze;
    }

    public List<String> getCorrectArray() {
        return correctArray;
    }

    public void setCorrectArray(List<String> correctArray) {
        this.correctArray = correctArray;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }
}
