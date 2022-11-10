package com.linkage.viewmodel.student.Practice;

import com.linkage.domain.Practice;
import com.linkage.utility.DateTimeUtil;
import com.linkage.viewmodel.BaseVM;

public class PracticePaperPageResponseVM extends BaseVM{

    private Integer id;

    private String name;

    private Integer questionCount;

    private String createTime;

    private Integer createUser;

    private Integer rankLevel;

    private String subjectName;

    private Integer paperType;

    private Integer frameTextContentId;

    private Integer technicalTypes;

    private Integer limitStartTime;

    private Integer limitEndTime;

    private Integer deleted;

    private Integer taskPracticeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getPaperType() {
        return paperType;
    }

    public void setPaperType(Integer paperType) {
        this.paperType = paperType;
    }

    public Integer getFrameTextContentId() {
        return frameTextContentId;
    }

    public void setFrameTextContentId(Integer frameTextContentId) {
        this.frameTextContentId = frameTextContentId;
    }

    public Integer getTechnicalTypes() {
        return technicalTypes;
    }

    public void setTechnicalTypes(Integer technicalTypes) {
        this.technicalTypes = technicalTypes;
    }

    public Integer getLimitStartTime() {
        return limitStartTime;
    }

    public void setLimitStartTime(Integer limitStartTime) {
        this.limitStartTime = limitStartTime;
    }

    public Integer getLimitEndTime() {
        return limitEndTime;
    }

    public void setLimitEndTime(Integer limitEndTime) {
        this.limitEndTime = limitEndTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getTaskPracticeId() {
        return taskPracticeId;
    }

    public void setTaskPracticeId(Integer taskPracticeId) {
        this.taskPracticeId = taskPracticeId;
    }

    public static PracticePaperPageResponseVM from(Practice practice) {
        PracticePaperPageResponseVM vm = modelMapper.map(practice, PracticePaperPageResponseVM.class);
        vm.setCreateTime(DateTimeUtil.dateFormat(practice.getCreateTime()));
        return vm;
    }

    @Override
    public String toString() {
        return "PracticePaperPageResponseVM{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", questionCount=" + questionCount +
                ", createTime='" + createTime + '\'' +
                ", createUser=" + createUser +
                ", rankLevel=" + rankLevel +
                ", subjectName='" + subjectName + '\'' +
                ", paperType=" + paperType +
                ", frameTextContentId=" + frameTextContentId +
                ", technicalTypes=" + technicalTypes +
                ", limitStartTime=" + limitStartTime +
                ", limitEndTime=" + limitEndTime +
                ", deleted=" + deleted +
                ", taskPracticeId=" + taskPracticeId +
                '}';
    }
}
