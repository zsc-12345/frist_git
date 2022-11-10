package com.linkage.viewmodel.admin.practice;

import com.linkage.viewmodel.admin.exam.ExamPaperTitleItemVM;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
public class PracticePaperEditRequestVM {

    private Integer id;
    @NotNull
    private Integer technicalTypes;
    @NotNull
    private Integer rankLevel;
    @NotNull
    private Integer paperType;
    @NotBlank
    private String name;
    @NotNull
    private Integer suggestTime;

    private List<String> limitDateTime;

    @Size(min = 1,message = "请添加试卷标题")
    @Valid
    private List<ExamPaperTitleItemVM> titleItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTechnicalTypes() {
        return technicalTypes;
    }

    public void setTechnicalTypes(Integer technicalTypes) {
        this.technicalTypes = technicalTypes;
    }

    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
    }

    public Integer getPaperType() {
        return paperType;
    }

    public void setPaperType(Integer paperType) {
        this.paperType = paperType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSuggestTime() {
        return suggestTime;
    }

    public void setSuggestTime(Integer suggestTime) {
        this.suggestTime = suggestTime;
    }

    public List<String> getLimitDateTime() {
        return limitDateTime;
    }

    public void setLimitDateTime(List<String> limitDateTime) {
        this.limitDateTime = limitDateTime;
    }

    public List<ExamPaperTitleItemVM> getTitleItems() {
        return titleItems;
    }

    public void setTitleItems(List<ExamPaperTitleItemVM> titleItems) {
        this.titleItems = titleItems;
    }
}
