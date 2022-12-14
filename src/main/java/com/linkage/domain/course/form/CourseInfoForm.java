package com.linkage.domain.course.form;

import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
import lombok.Data;

import java.math.BigDecimal;

import com.linkage.base.BasePage;

@Data
public class CourseInfoForm   extends BasePage {

	private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "课程一级分类ID")
    private String subjectParentId;

    @ApiModelProperty(value = "二级分类ID")
    private String subjectId;

    @ApiModelProperty(value = "课程标题")
    private String title;


    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程简介")
    private String description;
    
    @ApiModelProperty(value = "课程资源Id")
    private String resourceId;



}
