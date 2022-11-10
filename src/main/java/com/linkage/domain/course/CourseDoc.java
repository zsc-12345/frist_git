package com.linkage.domain.course;

import java.util.Date;

import lombok.Data;

@Data
public class CourseDoc {
    private Integer id;

    private String name;

    private String url;

    private String type;

    private Integer courseId;

    private Integer status;

    private Date createTime;

    private Date modifyTime;

}