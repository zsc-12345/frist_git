package com.linkage.controller.admin;

import com.github.pagehelper.PageInfo;
import com.linkage.base.BaseApiController;
import com.linkage.base.RestResponse;
import com.linkage.domain.AuthEntity;
import com.linkage.domain.enums.AuthStatusEnum;
import com.linkage.service.AuthAndRoleRelationService;
import com.linkage.service.AuthService;
import com.linkage.utility.PageInfoHelper;
import com.linkage.viewmodel.role.AuthPageRequestVM;
import com.linkage.viewmodel.role.AuthResponseVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Api(tags = "权限管理Controller")
@RestController("AuthController")
@RequestMapping(value = "/api/admin/auth")
public class AuthController extends BaseApiController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private final AuthService authService;

    private final AuthAndRoleRelationService authAndRoleRelationService;

    public AuthController(AuthService authService, AuthAndRoleRelationService authAndRoleRelationService) {
        this.authService = authService;
        this.authAndRoleRelationService = authAndRoleRelationService;
    }

    @ApiOperation(value="权限查询任务")
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<AuthResponseVM> select(@PathVariable Integer id) {
        AuthEntity auth = authService.SelectAuth(id);
        AuthResponseVM authVm = AuthResponseVM.from(auth);
        return RestResponse.ok(authVm);
    }

    @ApiOperation("插入权限任务")
    @RequestMapping(value = "/insertAuth", method = RequestMethod.POST)
    public RestResponse<AuthEntity> insertAuth(@RequestBody @Valid AuthResponseVM model) {
        try {
            AuthEntity authExit = authService.selectAuthById(model.getId());
            if (null != authExit) {
                logger.error("");
                return new RestResponse<>(2, "权限已存在");
            }
            AuthEntity auth = modelMapper.map(model, AuthEntity.class);
            auth.setCreate_time(new Date());
            auth.setModify_time(new Date());
            authService.createAuth(auth);
            logger.info("RoleController.insertRole: 登录权限成功， Param " + auth.toString());
            return new RestResponse<>(1, "权限插入执行成功");
        }catch (Exception e){
            logger.error("RoleController.insertRole: 登录权限失败" + e.getMessage());
            return RestResponse.fail(2,"登录权限失败");
        }

    }

    @ApiOperation("删除权限任务")
    @RequestMapping(value = "/deleteAuth/{id}", method = RequestMethod.POST)
    public RestResponse deleteAuth(@PathVariable Integer id){
        try{
            authService.deldteAuth(id);
            logger.info("");
            return RestResponse.ok();
        }catch (Exception e){
            logger.error("");
            return RestResponse.fail(2,"权限删除失败");
        }
    }

    @ApiOperation("更新权限任务")
    @RequestMapping(value = "/updateAuth", method = RequestMethod.POST)
    public RestResponse updateAuth(@RequestBody @Valid AuthResponseVM model){
        AuthEntity auth = authService.selectAuthById(model.getId());
        try{
            if(auth.getId() == null){
                logger.error("AuthController.updateAuth: 权限更新失败,权限不存在  Param AuthId" + auth.getId());
                return new RestResponse<>(2, "权限不存在");
            }
            auth.setRemark(model.getRemark());
            auth.setData_level(model.getData_level());
            auth.setUrl(model.getUrl());
            auth.setModify_time(new Date());
            authService.updateAuth(auth);
            logger.info("AuthController.updateAuth: 权限更新成功 Param " + auth.toString());
            return RestResponse.ok();
        }catch (Exception e){
            logger.error("AuthController.updateAuth: 权限更新失败  Param authId" + auth.getId() + e.getMessage());
            return RestResponse.fail(2,"权限更新失败");
        }

    }

    @ApiOperation(value="权限状态更改任务")
    @RequestMapping(value = "/changeStatus/{id}", method = RequestMethod.POST)
    public RestResponse<Integer> changeStatus(@PathVariable Integer id) {
        try {
            AuthEntity auth = authService.selectAuthById(id);
            AuthStatusEnum authStatusEnum = AuthStatusEnum.fromCode(auth.getStatus());
            Integer newStatus = authStatusEnum == AuthStatusEnum.Enable ? AuthStatusEnum.Disable.getCode() : AuthStatusEnum.Enable.getCode();
            auth.setStatus(newStatus);
            auth.setModify_time(new Date());
            authService.updateByIdFilter(auth);
            logger.info("AuthController.changeStatus: 状态更新成功 Param id:" + id);
            return RestResponse.ok(newStatus);
        }catch (Exception e){
            logger.error("状态更新失败" + e.getMessage());
            return RestResponse.fail(2, "状态更新失败");
        }
    }

    @ApiOperation(value = "列表任务")
    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo<AuthResponseVM>> pageList(@RequestBody AuthPageRequestVM model) {
        try{
            PageInfo<AuthEntity> pageInfo = authService.authPage(model);
            PageInfo<AuthResponseVM> page = PageInfoHelper.copyMap(pageInfo, d -> AuthResponseVM.from(d));
            logger.info("RoleController.pageList: 权限列表获取成功");
            return RestResponse.ok(page);
        }catch (Exception e){
            logger.error("RoleController.pageList: 权限列表获取成功" + e.getMessage());
            return RestResponse.fail(2, "权限列表获取失败");
        }

    }

    @ApiOperation(value="权限名称任务")
    @RequestMapping(value = "/getAuthsList/{id}", method = RequestMethod.POST)
    public List<String> getAuthsList(@PathVariable Integer id) {
        List<String> AuthsList = authAndRoleRelationService.getAuthNameList(id);
        return AuthsList;
    }


}
