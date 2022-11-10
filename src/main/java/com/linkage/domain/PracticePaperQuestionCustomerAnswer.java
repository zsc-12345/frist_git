package com.linkage.domain;

import java.io.Serializable;
import java.util.Date;

public class PracticePaperQuestionCustomerAnswer implements Serializable {

    private static final long serialVersionUID = 3389482731220342366L;

    private Integer id;

    /**
     * 题目Id
     */
    private Integer questionId;

    /**
     * 试卷Id
     */
    private Integer practicePaperId;

    /**
     * 答案Id
     */
    private Integer practicePaperAnswerId;

    /**
     * 题型
     */
    private Integer questionType;

    /**
     * 学科
     */
    private Integer rankLevel;

    /**
     * 得分
     */
    private Integer customerScore;

    /**
     * 题目原始分数
     */
    private Integer questionScore;

    /**
     * 问题内容
     */
    private Integer questionTextContentId;

    /**
     * 做题答案
     */
    private String answer;

    /**
     * 做题内容
     */
    private Integer textContentId;

    /**
     * 是否正确
     */
    private Boolean doRight;

    /**
     * 做题人
     */
    private Integer createUser;

    private Date createTime;

    private Integer itemOrder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getPracticePaperId() {
        return practicePaperId;
    }

    public void setPracticePaperId(Integer practicePaperId) {
        this.practicePaperId = practicePaperId;
    }

    public Integer getPracticePaperAnswerId() {
        return practicePaperAnswerId;
    }

    public void setPracticePaperAnswerId(Integer practicePaperAnswerId) {
        this.practicePaperAnswerId = practicePaperAnswerId;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
    }

    public Integer getCustomerScore() {
        return customerScore;
    }

    public void setCustomerScore(Integer customerScore) {
        this.customerScore = customerScore;
    }

    public Integer getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(Integer questionScore) {
        this.questionScore = questionScore;
    }

    public Integer getQuestionTextContentId() {
        return questionTextContentId;
    }

    public void setQuestionTextContentId(Integer questionTextContentId) {
        this.questionTextContentId = questionTextContentId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getTextContentId() {
        return textContentId;
    }

    public void setTextContentId(Integer textContentId) {
        this.textContentId = textContentId;
    }

    public Boolean getDoRight() {
        return doRight;
    }

    public void setDoRight(Boolean doRight) {
        this.doRight = doRight;
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

    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }
}
