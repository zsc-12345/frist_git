package com.linkage.domain.plan;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class LearnPlan {

    private int planId;

    private String planName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date planStartDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date planEndDate;

    private String planCorn;

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

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

    @Override
    public String toString() {
        return "LearnPlan{" +
                "planId=" + planId +
                ", planName='" + planName + '\'' +
                ", planStartDate=" + planStartDate +
                ", planEndDate=" + planEndDate +
                ", planCorn='" + planCorn + '\'' +
                '}';
    }
}
