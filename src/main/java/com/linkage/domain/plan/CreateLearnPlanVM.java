package com.linkage.domain.plan;

import java.util.Date;
import java.util.List;

public class CreateLearnPlanVM {

    private String planName;

    private Date planStartDate;

    private Date planEndDate;

    private String planCorn;

    private int type;

    private Date planRelationStartDate;

    private String planRelationCorn;

    private String subjectId;

    private String examPaperId;

    private List<String> studentIds;

    private List<String> teacherIds;

    private List<String> adminIds;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Date getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    public Date getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getPlanCorn() {
        return planCorn;
    }

    public void setPlanCorn(String planCorn) {
        this.planCorn = planCorn;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getPlanRelationStartDate() {
        return planRelationStartDate;
    }

    public void setPlanRelationStartDate(Date planRelationStartDate) {
        this.planRelationStartDate = planRelationStartDate;
    }

    public String getPlanRelationCorn() {
        return planRelationCorn;
    }

    public void setPlanRelationCorn(String planRelationCorn) {
        this.planRelationCorn = planRelationCorn;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getExamPaperId() {
        return examPaperId;
    }

    public void setExamPaperId(String examPaperId) {
        this.examPaperId = examPaperId;
    }

    public List<String> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<String> studentIds) {
        this.studentIds = studentIds;
    }

    public List<String> getTeacherIds() {
        return teacherIds;
    }

    public void setTeacherIds(List<String> teacherIds) {
        this.teacherIds = teacherIds;
    }

    public List<String> getAdminIds() {
        return adminIds;
    }

    public void setAdminIds(List<String> adminIds) {
        this.adminIds = adminIds;
    }

    @Override
    public String toString() {
        return "CreateLearnPlanVM{" +
                "planName='" + planName + '\'' +
                ", planStartDate=" + planStartDate +
                ", planEndDate=" + planEndDate +
                ", planCorn='" + planCorn + '\'' +
                ", type=" + type +
                ", planRelationStartDate=" + planRelationStartDate +
                ", planRelationCorn='" + planRelationCorn + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", examPaperId='" + examPaperId + '\'' +
                ", studentIds=" + studentIds +
                ", teacherIds=" + teacherIds +
                ", adminIds=" + adminIds +
                '}';
    }
}
