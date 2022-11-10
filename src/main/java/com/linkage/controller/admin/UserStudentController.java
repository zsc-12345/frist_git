package com.linkage.controller.admin;

import com.linkage.base.BaseApiController;
import com.linkage.base.RestResponse;
import com.linkage.domain.UserStudent;
import com.linkage.service.UserStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Api(tags = "用户学生管理Controller")
@RestController("UserStudentController")
@RequestMapping(value = "/api/admin/UserStudent")
public class UserStudentController extends BaseApiController {
    private final UserStudentService userStudentService;

    private static final Logger logger = LoggerFactory.getLogger(UserStudentController.class);

    @Autowired
    public UserStudentController(UserStudentService userStudentService){
        this.userStudentService = userStudentService;
    }
    @ApiOperation(value="查询任务")
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse studentService(@PathVariable int id){
        try {
            UserStudent userStudent = userStudentService.selectById(id);
            logger.info("UserStudentController.studentService:用户学生查询成功, id" + userStudent.getId(),"user_id" + userStudent.getUserId(),"stu_id" + userStudent.getStuId());
            return RestResponse.ok(userStudent);
        }catch (Exception e){
            logger.error("用户学生查询失败");
            return RestResponse.fail(2,"用户学员查询失败");
        }
    }

    @ApiOperation(value="增加任务")
    @RequestMapping(value = "addStudent", method = RequestMethod.POST)
    public RestResponse addStudent(@RequestBody UserStudent userStudent){
        try {
            int users = userStudentService.add(userStudent);
            logger.info("UserStudentController.addStudent:用户学生增加成功, id" + userStudent.getId(),"user_id" + userStudent.getUserId(),"stu_id" + userStudent.getStuId());
            return RestResponse.ok(userStudent);
        }catch (Exception e){
            logger.error("用户学生查询失败" + e.getMessage());
            return RestResponse.fail(2,"用户学员增加失败");
        }
    }


    @ApiOperation(value="删除任务")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable("id") Integer id) {
        try {
            int userStudent = userStudentService.deleteById(id);
            logger.info("UserStudentController.delete:用户学生删除成功, id" + id);
            return RestResponse.ok(userStudent);
        }catch (Exception e){
            logger.error("用户学员删除失败,id " + id);
            return RestResponse.fail(2,"用户学员删除失败");
        }
    }

    @ApiOperation(value="修改任务")
    @RequestMapping(value = "updateByid", method = RequestMethod.POST)
    public RestResponse update(@RequestBody UserStudent userStudent) {
        try {
            int users = userStudentService.updateStudentById(userStudent);
            if( userStudent.getId() == null){
                logger.error("UserStudentController.update:用户学生更新失败,用户学生不存在" + userStudent);
                return RestResponse.fail(2,"用户学生不存在");
            }else {
                logger.info("UserStudentController.update:用户学生修改成功, userStudent" + userStudent);
                return RestResponse.ok(userStudent);
            }
        }catch (Exception e){
            logger.error("用户学员修改失败");
            return RestResponse.fail(2,"用户学员修改失败");
        }
    }
}
