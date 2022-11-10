package com.linkage.viewmodel.admin.practice;

import com.linkage.base.BasePage;

public class PracticePaperPageRequestVM extends BasePage {
    private Integer id;
    private Integer rankLevel;
    private Integer technicalTypes;
    private Integer paperType;
    private Integer taskPracticeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
    }

    public Integer getTechnicalTypes() {
        return technicalTypes;
    }

    public void setTechnicalTypes(Integer technicalTypes) {
        this.technicalTypes = technicalTypes;
    }

    public Integer getPaperType() {
        return paperType;
    }

    public void setPaperType(Integer paperType) {
        this.paperType = paperType;
    }

    public Integer getTaskPracticeId() {
        return taskPracticeId;
    }

    public void setTaskPracticeId(Integer taskPracticeId) {
        this.taskPracticeId = taskPracticeId;
    }
}
