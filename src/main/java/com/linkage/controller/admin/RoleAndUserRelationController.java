package com.linkage.controller.admin;

import com.linkage.base.BaseApiController;
import com.linkage.base.RestResponse;
import com.linkage.domain.Role;
import com.linkage.domain.RoleAndUserRelation;
import com.linkage.domain.User;
import com.linkage.service.RoleAndUserRelationService;
import com.linkage.viewmodel.role.RoleResponseVM;

import com.linkage.viewmodel.student.user.UserResponseVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "角色用户关系Controller")
@RestController("RoleAndUserRelationController")
@RequestMapping(value = "/api/admin/RoleAndUserRelation")
public class RoleAndUserRelationController extends BaseApiController {

    private static final Logger logger = LoggerFactory.getLogger(RoleAndUserRelationController.class);

    @Autowired
    private RoleAndUserRelationService roleAndUserRelationService;

    @ApiOperation("查找角色任务")
    @RequestMapping(value = "/selectRoleByUserId/{userid}", method = RequestMethod.POST)
    public RestResponse selectRoleByUserId(@PathVariable("userid") Integer userid){
        try{
            Role role  = roleAndUserRelationService.selectRoleByUserId(userid);
            RoleResponseVM roleResponseVM = RoleResponseVM.from(role);
            logger.info("RoleAndUserRelationController execute method: selectUserByRoleId.param userid:"+ userid );
            return RestResponse.ok(roleResponseVM);
        }catch (Exception e){
            logger.info("RoleAndUserRelationController execute method: selectUserByRoleId.param userid:"+ userid + "查询失败" + e.getMessage());
            return RestResponse.fail(2, "查询失败");
        }

    }

    @ApiOperation("查找用户任务")
    @RequestMapping(value = "/selectUserByRoleId/{roleId}", method = RequestMethod.POST)
    public RestResponse selectUserByRoleId(@PathVariable("roleId") Integer roleId){
        List<User> users = roleAndUserRelationService.selectUserByRoleId(roleId);
        List<UserResponseVM> stuVM = new ArrayList<>() ;
        List<com.linkage.viewmodel.admin.user.UserResponseVM> teaVM = new ArrayList<>() ;
        try {
            if (roleId == 1) {
                for (int i = 0; i < users.size(); i++) {
                    User user = users.get(i);
                    UserResponseVM stuVM1 = UserResponseVM.from(user);
                    stuVM.add(stuVM1);
                }
                logger.info("RoleAndUserRelationController execute method: selectUserByRoleId.param roleId:"+ roleId );
                return RestResponse.ok(stuVM);
            } else if (roleId == 3) {
                for (int i = 0; i < users.size(); i++) {
                    User user = users.get(i);
                    com.linkage.viewmodel.admin.user.UserResponseVM teaVM1 = com.linkage.viewmodel.admin.user.UserResponseVM.from(user);
                    teaVM.add(teaVM1);
                }
                logger.info("RoleAndUserRelationController execute method: selectUserByRoleId.param roleId:"+ roleId );
                return RestResponse.ok(teaVM);
            } else {
                logger.info("RoleAndUserRelationController execute method: selectUserByRoleId.param roleId:"+ roleId + "查询失败");
                return new RestResponse<>(2, "用户不存在");
            }
        }catch (Exception e){
            logger.error("RoleAndUserRelationController execute method: selectUserByRoleId.param roleId:" + roleId + " 用户查询失败" + e.getMessage());
            return RestResponse.fail(2, "用户查询失败");
        }
    }


    @ApiOperation("角色用户关系表插入任务")
    @RequestMapping(value = "/register/{roleId}", method = RequestMethod.POST)
    public RestResponse register(@PathVariable("roleId") Integer roleId) {
        List<Integer> users = roleAndUserRelationService.getUsersByRole(roleId);
        RoleAndUserRelation roleAndUserRelation = new RoleAndUserRelation();
        logger.info("RoleAndUserRelationController execute method: register. Param roleId=" + roleId.intValue() + ". users size=" + users.size());
        try {
            for(int i = 0; i < users.size(); i++){
                RoleAndUserRelation rr = roleAndUserRelationService.checkExist(roleId, users.get(i));
                if(rr == null) {
                    roleAndUserRelation.setUser_id(users.get(i));
                    roleAndUserRelation.setRole_id(roleId);
                    roleAndUserRelationService.addUserByRole(roleAndUserRelation);
                }
            }
            return  new RestResponse<>(1, "角色用户关系插入任务执行成功");
        }catch (Exception e) {
            logger.error("角色用户关系插入任务执行失败: " + e.getMessage());
            return RestResponse.fail(2,"角色用户关系插入任务执行失败");
        }
    }

    @ApiOperation("角色用户关系表删除任务")
    @RequestMapping(value = "/deleteUserId/{userId}", method = RequestMethod.POST)
    public RestResponse deleteUserId(@PathVariable("userId") Integer userId) {
        Role role  = roleAndUserRelationService.selectRoleByUserId(userId);
        RoleAndUserRelation rr = roleAndUserRelationService.checkExist(role.getId(), userId);
        try{
            if(rr != null){
                roleAndUserRelationService.deleteUserId(userId);
                logger.info("RoleAndUserRelationController execute method: deleteUserId.param userId:" + userId + "删除用户成功");
                return RestResponse.ok();
            }else{
                logger.info("RoleAndUserRelationController execute method: deleteUserId.param userId:" + userId + "删除用户不存在");
                return RestResponse.ok();
            }
        }catch (Exception e){
            logger.info("RoleAndUserRelationController execute method: deleteUserId.param userId:" + userId + "删除用户失败" + e.getMessage());
            return RestResponse.fail(2, "删除失败");
        }

    }

    @ApiOperation("角色用户关系表更新任务")
    @RequestMapping(value = "/updateRoleId/{roleId}", method = RequestMethod.POST)
    public RestResponse updateRoleId(@PathVariable("roleId") Integer roleId, Integer userId) {
        Role role  = roleAndUserRelationService.selectRoleByUserId(userId);
        RoleAndUserRelation rr = roleAndUserRelationService.checkExist(role.getId(), userId);
        try{
            if(rr != null){
                roleAndUserRelationService.updateRoleId(roleId, userId);
                logger.info("RoleAndUserRelationController execute method: updateRoleId.param userId:" + userId + "更新用户成功");
                return RestResponse.ok();
            }else {
                logger.error("RoleAndUserRelationController execute method: updateRoleId.param userId:" + userId + "更新用户不存在");
                return RestResponse.fail(2, "更新用户不存在");
            }
        }catch (Exception e){
            logger.error("RoleAndUserRelationController execute method: updateRoleId.param userId:" + userId + "更新失败" + e.getMessage());
            return RestResponse.fail(2, "更新失败");
        }
    }
}
