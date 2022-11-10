package com.linkage.domain;

import java.io.Serializable;
import java.util.Date;

public class Practice implements Serializable {

    private static final long serialVersionUID = 8509645224550501395L;

    private Integer id;

    /**
     * 试卷名称
     */
    private String name;

    /**
     * 级别
     */
    private Integer rankLevel;

    /**
     * 试卷类型( 1固定试卷 4.时段试卷 6.任务试卷)
     */
    private Integer paperType;

    /**
     * 技术类型
     */
    private Integer technicalTypes;

    /**
     * 题目数量
     */
    private Integer questionCount;

    /**
     * 建议时长(分钟)
     */
    private Integer suggestTime;

    /**
     * 时段练习 开始时间
     */
    private Date limitStartTime;

    /**
     * 时段练习 结束时间
     */
    private Date limitEndTime;

    /**
     * 试卷框架 内容为JSON
     */
    private Integer frameTextContentId;

    private Integer createUser;

    private Date createTime;

    private Boolean deleted;

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

    public Integer getTechnicalTypes() {
        return technicalTypes;
    }

    public void setTechnicalTypes(Integer technicalTypes) {
        this.technicalTypes = technicalTypes;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Integer getSuggestTime() {
        return suggestTime;
    }

    public void setSuggestTime(Integer suggestTime) {
        this.suggestTime = suggestTime;
    }

    public Date getLimitStartTime() {
        return limitStartTime;
    }

    public void setLimitStartTime(Date limitStartTime) {
        this.limitStartTime = limitStartTime;
    }

    public Date getLimitEndTime() {
        return limitEndTime;
    }

    public void setLimitEndTime(Date limitEndTime) {
        this.limitEndTime = limitEndTime;
    }

    public Integer getFrameTextContentId() {
        return frameTextContentId;
    }

    public void setFrameTextContentId(Integer frameTextContentId) {
        this.frameTextContentId = frameTextContentId;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getTaskPracticeId() {
        return taskPracticeId;
    }

    public void setTaskPracticeId(Integer taskPracticeId) {
        this.taskPracticeId = taskPracticeId;
    }

    @Override
    public String toString() {
        return "Practice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rankLevel=" + rankLevel +
                ", paperType=" + paperType +
                ", technicalTypes=" + technicalTypes +
                ", questionCount=" + questionCount +
                ", suggestTime=" + suggestTime +
                ", limitStartTime=" + limitStartTime +
                ", limitEndTime=" + limitEndTime +
                ", frameTextContentId=" + frameTextContentId +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", deleted=" + deleted +
                ", taskPracticeId=" + taskPracticeId +
                '}';
    }
}
