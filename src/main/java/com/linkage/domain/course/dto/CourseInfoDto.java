package com.linkage.domain.course.dto;

import lombok.Data;

@Data
public class CourseInfoDto {
    private String id;
    private String title;
    private String cover;
    private String description;
    private String teacherName;//讲师名称
    private String levelOne;//一级分类名称
    private String levelTwo;//二级分类名称
}
