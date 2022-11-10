package com.linkage.viewmodel.student.Practice;

import com.linkage.base.BasePage;

import javax.validation.constraints.NotNull;

public class PracticePageVM extends BasePage {
    @NotNull
    private Integer paperType;
    private Integer rankLevel;
    private Integer technicalTypes;

    public Integer getPaperType() {
        return paperType;
    }

    public void setPaperType(Integer paperType) {
        this.paperType = paperType;
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
}
