package com.linkage.controller.wx.student;

import com.linkage.base.RestResponse;
import com.linkage.domain.ExamPaper;
import com.linkage.domain.Subject;
import com.linkage.service.ExamPaperService;
import com.linkage.service.SubjectService;
import com.linkage.utility.DateTimeUtil;
import com.linkage.utility.PageInfoHelper;
import com.linkage.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.linkage.viewmodel.student.exam.ExamPaperPageResponseVM;
import com.linkage.viewmodel.student.exam.ExamPaperPageVM;
import com.linkage.controller.wx.BaseWXApiController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "试卷管理Controller")
@Controller("WXStudentExamController")
@RequestMapping(value = "/api/wx/student/exampaper")
@ResponseBody
public class ExamPaperController extends BaseWXApiController {

    private final ExamPaperService examPaperService;
    private final SubjectService subjectService;

    @Autowired
    public ExamPaperController(ExamPaperService examPaperService, SubjectService subjectService) {
        this.examPaperService = examPaperService;
        this.subjectService = subjectService;
    }

    @ApiOperation(value = "试卷Id")
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> select(@PathVariable Integer id) {
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(id);
        return RestResponse.ok(vm);
    }

    @ApiOperation(value = "试卷列表")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperPageResponseVM>> pageList(@Valid ExamPaperPageVM model) {
        model.setLevelId(getCurrentUser().getUserLevel());
        PageInfo<ExamPaper> pageInfo = examPaperService.studentPage(model);
        PageInfo<ExamPaperPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamPaperPageResponseVM vm = modelMapper.map(e, ExamPaperPageResponseVM.class);
            Subject subject = subjectService.selectById(vm.getSubjectId());
            vm.setSubjectName(subject.getName());
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }
}
