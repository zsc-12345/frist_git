package com.linkage.controller.admin;

import com.github.pagehelper.PageInfo;
import com.linkage.base.BaseApiController;
import com.linkage.base.RestResponse;
import com.linkage.domain.Teacher;
import com.linkage.domain.enums.TeacherStatusEnum;
import com.linkage.service.TeacherService;
import com.linkage.utility.PageInfoHelper;
import com.linkage.viewmodel.admin.teacher.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Api(tags = "讲师管理Controller")
@RestController("TeacherController")
@RequestMapping(value = "/api/admin/teacher")
public class TeacherController extends BaseApiController {

    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    private final TeacherService teacherService;


    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @ApiOperation(value = "讲师列表")
    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo<TeacherResponseVM>> pageList(@RequestBody TeacherPageRequestVM model) {
        try{
            PageInfo<Teacher> pageInfo = teacherService.teacherPage(model);
            PageInfo<TeacherResponseVM> page = PageInfoHelper.copyMap(pageInfo, d -> TeacherResponseVM.from(d));
            logger.info("TeacherController.pageList: 讲师列表获取成功");
            return RestResponse.ok(page);
        }catch (Exception e){
            logger.error("TeacherController.insertRole: 讲师列表获取成功" + e.getMessage());
            return RestResponse.fail(2, "讲师列表获取失败");
        }

    }
    @ApiOperation(value="讲师查询任务")
    @RequestMapping(value = "/selectTeacher/{id}", method = RequestMethod.POST)
    public RestResponse<TeacherResponseVM> selectTeacher(@PathVariable Integer id){
        try{
            Teacher teacher = teacherService.selectByTeacherId(id);
            TeacherResponseVM teacherResponseVM = TeacherResponseVM.from(teacher);
            logger.info("TeacherController.selectTeacher: 讲师查找成功 Param" + teacherResponseVM.toString());
            return RestResponse.ok(teacherResponseVM);
        }catch (Exception e){
            logger.error("TeacherController.updateTeacher: 讲师查找失败" + e.getMessage());
            return RestResponse.fail(2,"讲师查找失败");
        }

    }
    @ApiOperation("讲师更改")
    @RequestMapping(value = "/insertTeacher", method = RequestMethod.POST)
    public RestResponse<Teacher> insertTeacher(@RequestBody @Valid TeacherResponseVM model) {


            Teacher teacher = modelMapper.map(model, Teacher.class);

            if (model.getId() == null) {
                Teacher existTeacher = teacherService.getTeacherByTeacherId(model.getId());
                if (null != existTeacher) {
                    logger.error("TeacherController.insertTeacher: 讲师角色已经存在， Param existTeacher" + existTeacher);
                    return new RestResponse<>(2, "角色已存在");
                }
            teacher.setCreateTime(new Date());
            teacher.setModifyTime(new Date());
            teacher.setLastActiveTime(new Date());
            teacherService.insertTeacher(teacher);
            logger.info("TeacherController.insertTeacher: 登录讲师成功， Param " + teacher.toString());
            return new RestResponse<>(1, "讲师插入成功");

        }else{teacher.setTeacherName(model.getTeacherName());
                teacher.setModifyTime(new Date());
                teacherService.updateTeacher(teacher);
                logger.info("TeacherController.updateTeacher: 讲师角色更新成功 Param " + teacher.toString());
                return RestResponse.ok();
            }
    }

    @ApiOperation(value="讲师删除")
    @RequestMapping(value = "/deleteTeacher/{id}", method = RequestMethod.POST)
    public RestResponse deleteTeacher(@PathVariable Integer id){
        try{
            teacherService.deleteById(id);
            logger.info("TeacherService.deleteTeacher: 讲师已经删除， Param teacherId" + id);
            return RestResponse.ok();
        }catch (Exception e){
            logger.error("TeacherController.deleteTeacher: 讲师删除失败， Param teacherId" + id + e.getMessage());
            return RestResponse.fail(2,"讲师删除失败");
        }
    }


    @ApiOperation(value="讲师状态更改任务")
    @RequestMapping(value = "/changeStatus/{id}", method = RequestMethod.POST)
    public RestResponse<Integer> changeStatus(@PathVariable Integer id) {
        try {
            Teacher tea = teacherService.getTeacherByTeacherId(id);
            TeacherStatusEnum teaStatusEnum = TeacherStatusEnum.fromCode(tea.getStatus());
            Integer newStatus = teaStatusEnum == TeacherStatusEnum.Enable ? TeacherStatusEnum.Disable.getCode() : TeacherStatusEnum.Enable.getCode();
            tea.setStatus(newStatus);
            tea.setModifyTime(new Date());
            teacherService.updateByIdFilter(tea);
            return RestResponse.ok(newStatus);
        }catch (Exception e){
            return RestResponse.fail(2, "状态更新失败");
        }
    }

}
