package com.linkage.controller.student;


import com.linkage.base.BaseApiController;
import com.linkage.base.RestResponse;
import com.linkage.domain.Subject;
import com.linkage.domain.User;
import com.linkage.service.SubjectService;
import com.linkage.viewmodel.student.education.SubjectEditRequestVM;
import com.linkage.viewmodel.student.education.SubjectVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "学生学科管理Controller")
@RestController("StudentEducationController")
@RequestMapping(value = "/api/student/education")
public class EducationController extends BaseApiController {

    private final SubjectService subjectService;

    @Autowired
    public EducationController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @ApiOperation(value="学科列表任务")
    @RequestMapping(value = "/subject/list", method = RequestMethod.POST)
    public RestResponse<List<SubjectVM>> list() {
        User user = getCurrentUser();
        List<Subject> subjects = subjectService.getSubjectByLevel(user.getUserLevel());
        List<SubjectVM> subjectVMS = subjects.stream().map(d -> {
            SubjectVM subjectVM = modelMapper.map(d, SubjectVM.class);
            subjectVM.setId(String.valueOf(d.getId()));
            return subjectVM;
        }).collect(Collectors.toList());
        return RestResponse.ok(subjectVMS);
    }

    @ApiOperation(value="学科查询任务")
    @RequestMapping(value = "/subject/select/{id}", method = RequestMethod.POST)
    public RestResponse<SubjectEditRequestVM> select(@PathVariable Integer id) {
        Subject subject = subjectService.selectById(id);
        SubjectEditRequestVM vm = modelMapper.map(subject, SubjectEditRequestVM.class);
        return RestResponse.ok(vm);
    }

}
