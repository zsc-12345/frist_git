package com.linkage.domain.plan;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PlanRelation {
    private int planRelationId;
    private int learnPlanId;
    private int type;
    private String name;
    private String outId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date planRelationStartDate;
    private String planRelationCorn;

    public int getPlanRelationId() {
        return planRelationId;
    }

    public void setPlanRelationId(int planRelationId) {
        this.planRelationId = planRelationId;
    }

    public int getLearnPlanId() {
        return learnPlanId;
    }

    public void setLearnPlanId(int learnPlanId) {
        this.learnPlanId = learnPlanId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
