package com.linkage.domain.course;

import java.util.Date;

import lombok.Data;

@Data
public class CourseChapter {
    private Integer id;

    private Integer courseId;

    private String title;

    private Integer status;

    private Date createTime;

    private Date modifyTime;

}