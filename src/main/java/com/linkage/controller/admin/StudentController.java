package com.linkage.controller.admin;

import com.github.pagehelper.PageInfo;
import com.linkage.base.BaseApiController;
import com.linkage.base.RestResponse;
import com.linkage.domain.Role;
import com.linkage.domain.Student;
import com.linkage.domain.enums.StudentStatusEnum;
import com.linkage.service.StudentService;
import com.linkage.utility.PageInfoHelper;
import com.linkage.viewmodel.role.RolePageRequestVM;
import com.linkage.viewmodel.role.RoleResponseVM;
import com.linkage.viewmodel.student.StudentPageRequestVM;
import com.linkage.viewmodel.student.StudentResponseVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;


@Api(tags = "学生管理Controller")
@RestController("StudentController")
@RequestMapping(value = "/api/admin/student")

public class StudentController extends BaseApiController {
    @Autowired
    private StudentService studentService ;

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);


    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @ApiOperation(value="查询学员任务")
    @RequestMapping(value = "/selectByStudentId/{Id}", method = RequestMethod.POST)
    public RestResponse<StudentResponseVM> selectByStudentId(@PathVariable Integer Id){
        try{
            Student student = studentService.selectByStudentId(Id);
            StudentResponseVM studentResponseVM = StudentResponseVM.from(student);
            logger.info("StudentController.StudentService: 学生查找成功 Param" + studentResponseVM.toString());
            return RestResponse.ok(studentResponseVM);
        }catch (Exception e){
            logger.error("StudentController.StudentService:学生查找失败" + e.getMessage());
            return RestResponse.fail(2,"学生查找失败");
        }

    }
    @ApiOperation(value = "列表任务")
    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo<StudentResponseVM>> pageList(@RequestBody StudentPageRequestVM model) {
        try{
            PageInfo<Student> pageInfo = studentService.studentPage(model);
            PageInfo<StudentResponseVM> page = PageInfoHelper.copyMap(pageInfo, d -> StudentResponseVM.from(d));
            logger.info("StudentController.pageList: 学员列表获取成功");
            return RestResponse.ok(page);
        }catch (Exception e){
            logger.error("RoleController.insertRole: 学员列表获取成功" + e.getMessage());
            return RestResponse.fail(2, "学员列表获取失败");
        }

    }

    @ApiOperation(value="增加任务")
    @RequestMapping(value = "/insertStudent", method = RequestMethod.POST)
    public RestResponse<Student> insertStudent(@RequestBody @Valid StudentResponseVM model){
        try {
            Student stud = studentService.getByStudentId(model.getId());
            if (null != stud) {
                logger.error("StudentController.insertStudent: 学生已存在" + stud);
                return new RestResponse<>(2, "学生已存在");
            }
            Student student = modelMapper.map(model, Student.class);

            student.setCreate_time(new Date());
            student.setModify_time(new Date());
            studentService.insertByStudent(student);
            logger.info("StudentController.addStudent: 学生增加成功, Param " + student.toString());
            return new RestResponse<>(1,"学员增加成功");
        }catch (Exception e){
            logger.error("StudentController.addStudent:学生增加失败" + e.getMessage());
            return RestResponse.fail(2,"学生增加失败");
        }
    }


    @ApiOperation(value="删除任务")
    @RequestMapping(value = "/deleteStudent/{Id}", method = RequestMethod.POST)
    public RestResponse deleteStudent(@PathVariable Integer Id) {
        try {
            studentService.deleteStudent(Id);
            logger.info("StudentController.delete:学生删除成功,id" + Id);
            return RestResponse.ok();
        }catch (Exception e){
            logger.error("StudentController.delete:学生删除失败,id" + Id + e.getMessage());
            return RestResponse.fail(2,"学生删除失败");
        }
    }

    @ApiOperation(value="修改任务")
    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
    public RestResponse updateStudent(@RequestBody @Valid StudentResponseVM model) {
        Student student = studentService.getByStudentId(model.getId());
        try {
            if(student.getId() == null){
                logger.error("StudentController.updateStudent:学生修改失败,student" + student.getId());
                return new RestResponse<>(2,"学员不存在");
            }
            student.setDescrption(model.getDescrption());
            student.setStatus(model.getStatus());
            student.setModify_time(new Date());
            student.setCreate_time(new Date());
            studentService.updateStudent(student);
            logger.info("StudentController.updateStudent:学生修改成功,Param" + student.toString());
            return RestResponse.ok();
        }catch (Exception e){
            logger.error("StudentController.update:学生修改失败 Param Id" +student.getId() + e.getMessage());
            return RestResponse.fail(2,"学生修改失败");
        }
    }
    @ApiOperation(value="角色状态更改任务")
    @RequestMapping(value = "/changeStatus/{id}", method = RequestMethod.POST)
    public RestResponse<Integer> changeStatus(@PathVariable Integer id) {
        try {
            Student student = studentService.getByStudentId(id);
            StudentStatusEnum studentStatusEnum = StudentStatusEnum.fromCode(student.getStatus());
            Integer newStatus = studentStatusEnum == StudentStatusEnum.Enable ? StudentStatusEnum.Disable.getCode() : StudentStatusEnum.Enable.getCode();
            student.setStatus(newStatus);
            student.setModify_time(new Date());
            studentService.updateByIdFilter(student);
            logger.info("StudentController.changeStatus: 状态更新成功 Param id:" + id);
            return RestResponse.ok(newStatus);
        }catch (Exception e){
            logger.error("状态更新失败" + e.getMessage());
            return RestResponse.fail(2, "状态更新失败");
        }
    }
}
