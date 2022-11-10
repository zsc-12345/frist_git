package com.linkage.domain.course;

import java.util.Date;

import lombok.Data;

@Data
public class Course {
    private Integer id;

    private String title;

    private Integer lessonNum;

    private String cover;

    private Long version;

    private String status;

    private Date createTime;

    private Date modifyTime;

}