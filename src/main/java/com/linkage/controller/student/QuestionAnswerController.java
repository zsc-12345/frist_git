package com.linkage.controller.student;

import com.linkage.base.BaseApiController;
import com.linkage.base.RestResponse;
import com.linkage.domain.ExamPaperQuestionCustomerAnswer;
import com.linkage.domain.Subject;
import com.linkage.domain.TextContent;
import com.linkage.domain.question.QuestionObject;
import com.linkage.service.ExamPaperQuestionCustomerAnswerService;
import com.linkage.service.QuestionService;
import com.linkage.service.SubjectService;
import com.linkage.service.TextContentService;
import com.linkage.utility.DateTimeUtil;
import com.linkage.utility.HtmlUtil;
import com.linkage.utility.JsonUtil;
import com.linkage.utility.PageInfoHelper;
import com.linkage.viewmodel.admin.question.QuestionEditRequestVM;
import com.linkage.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.linkage.viewmodel.student.question.answer.QuestionAnswerVM;
import com.linkage.viewmodel.student.question.answer.QuestionPageStudentRequestVM;
import com.linkage.viewmodel.student.question.answer.QuestionPageStudentResponseVM;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "学生问题答案管理Controller")
@RestController("StudentQuestionAnswerController")
@RequestMapping(value = "/api/student/question/answer")
public class QuestionAnswerController extends BaseApiController {

    private final ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    private final QuestionService questionService;
    private final TextContentService textContentService;
    private final SubjectService subjectService;

    @Autowired
    public QuestionAnswerController(ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService, QuestionService questionService, TextContentService textContentService, SubjectService subjectService) {
        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
        this.questionService = questionService;
        this.textContentService = textContentService;
        this.subjectService = subjectService;
    }

    @ApiOperation(value="学生答案列表任务")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<QuestionPageStudentResponseVM>> pageList(@RequestBody QuestionPageStudentRequestVM model) {
        model.setCreateUser(getCurrentUser().getId());
        PageInfo<ExamPaperQuestionCustomerAnswer> pageInfo = examPaperQuestionCustomerAnswerService.studentPage(model);
        PageInfo<QuestionPageStudentResponseVM> page = PageInfoHelper.copyMap(pageInfo, q -> {
            Subject subject = subjectService.selectById(q.getSubjectId());
            QuestionPageStudentResponseVM vm = modelMapper.map(q, QuestionPageStudentResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(q.getCreateTime()));
            TextContent textContent = textContentService.selectById(q.getQuestionTextContentId());
            QuestionObject questionObject = JsonUtil.toJsonObject(textContent.getContent(), QuestionObject.class);
            String clearHtml = HtmlUtil.clear(questionObject.getTitleContent());
            vm.setShortTitle(clearHtml);
            vm.setSubjectName(subject.getName());
            return vm;
        });
        return RestResponse.ok(page);
    }


    @ApiOperation(value="学生答案查询任务")
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<QuestionAnswerVM> select(@PathVariable Integer id) {
        QuestionAnswerVM vm = new QuestionAnswerVM();
        ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer = examPaperQuestionCustomerAnswerService.selectById(id);
        ExamPaperSubmitItemVM questionAnswerVM = examPaperQuestionCustomerAnswerService.examPaperQuestionCustomerAnswerToVM(examPaperQuestionCustomerAnswer);
        QuestionEditRequestVM questionVM = questionService.getQuestionEditRequestVM(examPaperQuestionCustomerAnswer.getQuestionId());
        vm.setQuestionVM(questionVM);
        vm.setQuestionAnswerVM(questionAnswerVM);
        return RestResponse.ok(vm);
    }

}
