package com.linkage.controller.admin;

import com.github.pagehelper.PageInfo;
import com.linkage.base.BaseApiController;
import com.linkage.base.RestResponse;
import com.linkage.domain.Practice;
import com.linkage.service.PracticeService;
import com.linkage.utility.DateTimeUtil;
import com.linkage.utility.PageInfoHelper;
import com.linkage.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.linkage.viewmodel.admin.practice.PracticePaperEditRequestVM;
import com.linkage.viewmodel.admin.practice.PracticePaperPageRequestVM;
import com.linkage.viewmodel.admin.practice.PracticeResponseVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "练习管理Controller")
@RestController("PracticeController")
@RequestMapping(value = "/api/admin/practice/paper")
public class PracticeController extends BaseApiController {

    private final PracticeService practiceService;

    @Autowired
    public PracticeController(PracticeService practiceService) {
        this.practiceService = practiceService;
    }


    @ApiOperation(value = "练习列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<PracticeResponseVM>> pageList(@RequestBody PracticePaperPageRequestVM model) {
        PageInfo<Practice> pageInfo = practiceService.page(model);
        PageInfo<PracticeResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            PracticeResponseVM vm = modelMapper.map(e, PracticeResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }
    @ApiOperation(value = "练习任务")
    @RequestMapping(value = "/taskExamPage", method = RequestMethod.POST)
    public RestResponse<PageInfo<PracticeResponseVM>> taskExamPageList(@RequestBody PracticePaperPageRequestVM model) {
        PageInfo<Practice> pageInfo = practiceService.taskExamPage(model);
        PageInfo<PracticeResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            PracticeResponseVM vm = modelMapper.map(e, PracticeResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }

    @ApiOperation(value = "练习编辑任务")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<PracticePaperEditRequestVM> edit(@RequestBody @Valid PracticePaperEditRequestVM model) {
        Practice practice = practiceService.savePaperFromVM(model, getCurrentUser());
        PracticePaperEditRequestVM newVM = practiceService.examPaperToVM(practice.getId());
        return RestResponse.ok(newVM);
    }

    @ApiOperation(value = "练习查询任务")
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<PracticePaperEditRequestVM> select(@PathVariable Integer id) {
        PracticePaperEditRequestVM vm = practiceService.examPaperToVM(id);
        return RestResponse.ok(vm);
    }

    @ApiOperation(value = "练习删除任务")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {
        Practice practice = practiceService.selectById(id);
        practice.setDeleted(true);
        practiceService.updateByIdFilter(practice);
        return RestResponse.ok();
    }
}
