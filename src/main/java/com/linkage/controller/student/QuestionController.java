package com.linkage.controller.student;

import com.linkage.base.BaseApiController;
import com.linkage.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "学生问题管理Controller")
@RestController("StudentQuestionController")
@RequestMapping(value = "/api/student/question")
public class QuestionController extends BaseApiController {

//    private final QuestionService questionService;
// @Autowired
//    public QuestionController(QuestionService questionService) {
//        this.questionService = questionService;
//    }

    @Autowired
    private QuestionService questionService;



}
