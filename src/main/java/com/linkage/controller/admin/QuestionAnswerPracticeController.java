package com.linkage.controller.admin;

import com.github.pagehelper.PageInfo;
import com.linkage.base.BaseApiController;
import com.linkage.base.RestResponse;
import com.linkage.domain.ExamPaperQuestionCustomerAnswer;
import com.linkage.domain.PracticePaperQuestionCustomerAnswer;
import com.linkage.domain.Subject;
import com.linkage.domain.TextContent;
import com.linkage.domain.question.QuestionObject;
import com.linkage.service.*;
import com.linkage.utility.DateTimeUtil;
import com.linkage.utility.HtmlUtil;
import com.linkage.utility.JsonUtil;
import com.linkage.utility.PageInfoHelper;
import com.linkage.viewmodel.admin.question.QuestionEditRequestVM;
import com.linkage.viewmodel.student.Practice.PracticePaperSubmitItemVM;
import com.linkage.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.linkage.viewmodel.student.question.answer.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "学生练习问题答案管理Controller")
@RestController("AdminQuestionAnswerController")
@RequestMapping(value = "/api/admin/question/answer")
public class QuestionAnswerPracticeController extends BaseApiController {
    private final PracticePaperQuestionCustomerAnswerService practicePaperQuestionCustomerAnswerService;
    private final QuestionService questionService;
    private final TextContentService textContentService;
    private final SubjectService subjectService;

    @Autowired
    public QuestionAnswerPracticeController(PracticePaperQuestionCustomerAnswerService practicePaperQuestionCustomerAnswerService, QuestionService questionService, TextContentService textContentService, SubjectService subjectService) {
        this.practicePaperQuestionCustomerAnswerService = practicePaperQuestionCustomerAnswerService;
        this.questionService = questionService;
        this.textContentService = textContentService;
        this.subjectService = subjectService;
    }
    @ApiOperation(value="学生练习答案列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<QuestionPagePracticeStudentResponseVM>> pageList(@RequestBody QuestionPagePracticeStudentRequestVM model) {
        model.setCreateUser(getCurrentUser().getId());
        PageInfo<PracticePaperQuestionCustomerAnswer> pageInfo = practicePaperQuestionCustomerAnswerService.studentPage(model);
        PageInfo<QuestionPagePracticeStudentResponseVM> page = PageInfoHelper.copyMap(pageInfo, q -> {
            Subject subject = subjectService.selectById(q.getRankLevel());
            QuestionPagePracticeStudentResponseVM vm = modelMapper.map(q, QuestionPagePracticeStudentResponseVM.class);
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


    @ApiOperation(value="学生练习答案查询任务")
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<QuestionPracticeAnswerVM> select(@PathVariable Integer id) {
        QuestionPracticeAnswerVM vm = new QuestionPracticeAnswerVM();
        PracticePaperQuestionCustomerAnswer practicePaperQuestionCustomerAnswer = practicePaperQuestionCustomerAnswerService.selectById(id);
        PracticePaperSubmitItemVM questionAnswerVM = practicePaperQuestionCustomerAnswerService.examPaperQuestionCustomerAnswerToVM(practicePaperQuestionCustomerAnswer);
        QuestionEditRequestVM questionVM = questionService.getQuestionEditRequestVM(practicePaperQuestionCustomerAnswer.getQuestionId());
        vm.setQuestionVM(questionVM);
        vm.setQuestionAnswerVM(questionAnswerVM);
        return RestResponse.ok(vm);
    }
}
