package com.linkage.controller.admin;

import com.github.pagehelper.PageInfo;
import com.linkage.base.BaseApiController;
import com.linkage.base.RestResponse;
import com.linkage.domain.AuthAndRoleRelation;
import com.linkage.domain.Role;
import com.linkage.domain.User;
import com.linkage.domain.enums.RoleStatusEnum;
import com.linkage.service.AuthAndRoleRelationService;
import com.linkage.service.RoleService;
import com.linkage.utility.PageInfoHelper;
import com.linkage.viewmodel.role.ChangeAuthsVM;
import com.linkage.viewmodel.role.RolePageRequestVM;
import com.linkage.viewmodel.role.RoleResponseVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = "角色Controller")
@RestController("RoleController")
@RequestMapping(value = "/api/admin/role")
public class RoleController extends BaseApiController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    private final RoleService roleService;

    private final AuthAndRoleRelationService authAndRoleRelationService;

    public RoleController(RoleService roleService, AuthAndRoleRelationService authAndRoleRelationService) {
        this.authAndRoleRelationService = authAndRoleRelationService;
        this.roleService = roleService;
    }

    @ApiOperation(value = "列表任务")
    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo<RoleResponseVM>> pageList(@RequestBody RolePageRequestVM model) {
        try{
            PageInfo<Role> pageInfo = roleService.rolePage(model);
            PageInfo<RoleResponseVM> page = PageInfoHelper.copyMap(pageInfo, d -> RoleResponseVM.from(d));
            logger.info("RoleController.pageList: 角色列表获取成功");
            return RestResponse.ok(page);
        }catch (Exception e){
            logger.error("RoleController.insertRole: 角色列表获取成功" + e.getMessage());
            return RestResponse.fail(2, "角色列表获取失败");
        }

    }


    @ApiOperation("插入角色任务")
    @RequestMapping(value = "/insertRole", method = RequestMethod.POST)
    public RestResponse<Role> insertRole(@RequestBody @Valid RoleResponseVM model) {
        try {
            Role roleExitId = roleService.getRoleByRoleId(model.getId());
            if (null != roleExitId) {
                logger.error("RoleController.insertRole: 登录角色已经存在， Param roleExitId" + roleExitId);
                return new RestResponse<>(2, "角色已存在");
            }
            Role role = modelMapper.map(model, Role.class);
            role.setCreate_time(new Date());
            role.setModify_time(new Date());
            roleService.insertRole(role);
            logger.info("RoleController.insertRole: 登录角色成功， Param " + role.toString());
            return new RestResponse<>(1, "角色插入执行成功");
        }catch (Exception e){
            logger.error("RoleController.insertRole: 登录角色失败" + e.getMessage());
            return RestResponse.fail(2,"登录角色失败");
        }

    }

    @ApiOperation("删除角色任务")
    @RequestMapping(value = "/deleteRole/{id}", method = RequestMethod.POST)
    public RestResponse deleteRole(@PathVariable Integer id){
        try{
            roleService.deleteById(id);
            logger.info("RoleController.deleteRole: 角色已经删除， Param roleId" + id);
            return RestResponse.ok();
        }catch (Exception e){
            logger.error("RoleController.deleteRole: 角色删除失败， Param roleId" + id + e.getMessage());
            return RestResponse.fail(2,"角色删除失败");
        }
    }

    @ApiOperation("更新角色任务")
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    public RestResponse updateRole(@RequestBody @Valid RoleResponseVM model){
        Role role = roleService.getRoleByRoleId(model.getId());
        try{
            if(role.getId() == null){
                logger.error("RoleController.updateRole: 角色更新失败,角色不存在  Param roleId" + role.getId());
                return new RestResponse<>(2, "角色不存在");
            }
            role.setRemark(model.getRemark());
            role.setType(model.getType());
            role.setName(model.getName());
            role.setModify_time(new Date());
            roleService.updateRole(role);
            logger.info("RoleController.updateRole: 角色更新成功 Param " + role.toString());
            return RestResponse.ok();
        }catch (Exception e){
            logger.error("RoleController.updateRole: 角色更新失败  Param roleId" + role.getId() + e.getMessage());
            return RestResponse.fail(2,"角色更新失败");
        }

    }

    @ApiOperation("查找角色任务")
    @RequestMapping(value = "/selectRole/{id}", method = RequestMethod.POST)
    public RestResponse<RoleResponseVM> selectRole(@PathVariable Integer id){
        try{
            Role role = roleService.selectByRoleId(id);
            RoleResponseVM roleResponseVM = RoleResponseVM.from(role);
            logger.info("RoleController.selectRole: 角色查找成功 Param" + roleResponseVM.toString());
            return RestResponse.ok(roleResponseVM);
        }catch (Exception e){
            logger.error("RoleController.updateRole: 角色查找失败" + e.getMessage());
            return RestResponse.fail(2,"角色查找失败");
        }

    }

    @ApiOperation(value="角色状态更改任务")
    @RequestMapping(value = "/changeStatus/{id}", method = RequestMethod.POST)
    public RestResponse<Integer> changeStatus(@PathVariable Integer id) {
        try {
            Role role = roleService.getRoleByRoleId(id);
            RoleStatusEnum roleStatusEnum = RoleStatusEnum.fromCode(role.getStatus());
            Integer newStatus = roleStatusEnum == RoleStatusEnum.Enable ? RoleStatusEnum.Disable.getCode() : RoleStatusEnum.Enable.getCode();
            role.setStatus(newStatus);
            role.setModify_time(new Date());
            roleService.updateByIdFilter(role);
            logger.info("RoleController.changeStatus: 状态更新成功 Param id:" + id);
            return RestResponse.ok(newStatus);
        }catch (Exception e){
            logger.error("状态更新失败" + e.getMessage());
            return RestResponse.fail(2, "状态更新失败");
        }
    }

    @ApiOperation(value="角色权限更改任务")
    @RequestMapping(value = "/changeAuths", method = RequestMethod.POST)
    public RestResponse changeAuths(@RequestBody ChangeAuthsVM changeAuthsVM) {
        int roleId = Integer.parseInt(changeAuthsVM.getRoleId());
        //先删除该角色已经赋予的权限
        authAndRoleRelationService.deleteAuthRoleRLN(roleId);
        //创建角色权限数组
        List<AuthAndRoleRelation> authRRs = new ArrayList<>();
        String[] authIds = changeAuthsVM.getIds().split(",");
        for(int i = 0; i < authIds.length; i ++){
            AuthAndRoleRelation auth = new AuthAndRoleRelation();
            auth.setRole_id(roleId);
            auth.setAuth_id(Integer.parseInt(authIds[i]));
            authRRs.add(auth);
        }
        if(!authRRs.isEmpty()){
            authAndRoleRelationService.insertAuthRoleRLN(authRRs);
            return new RestResponse<>(1, "角色权限变更执行成功");
        }else{
            return RestResponse.fail(2, "角色权限变更执行失败");
        }

    }


}
