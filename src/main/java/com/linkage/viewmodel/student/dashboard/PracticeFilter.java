package com.linkage.viewmodel.student.dashboard;

import java.util.Date;

public class PracticeFilter {

    private Integer userId;
    private Date dateTime;
    private Integer practicePaperType;
    private Integer technicalTypes;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getPracticePaperType() {
        return practicePaperType;
    }

    public void setPracticePaperType(Integer practicePaperType) {
        this.practicePaperType = practicePaperType;
    }

    public Integer getTechnicalTypes() {
        return technicalTypes;
    }

    public void setTechnicalTypes(Integer technicalTypes) {
        this.technicalTypes = technicalTypes;
    }
}
