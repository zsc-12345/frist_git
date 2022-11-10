package com.linkage.controller.dingtalk;

import java.util.*;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.dingtalk.api.response.OapiDepartmentListResponse.Department;
import com.linkage.base.RestResponse;
import com.linkage.configuration.dingtalk.AppConfig;
import com.linkage.domain.dingtalk.DepartmentDTO;
import com.linkage.domain.dingtalk.ServiceResult;
import com.linkage.domain.dingtalk.UserDTO;
import com.linkage.service.dingtalk.DepartmentService;
import com.linkage.service.dingtalk.DingTalkUserService;
import com.linkage.service.dingtalk.TokenService;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import static com.linkage.configuration.dingtalk.UrlConstant.*;


/**
 * 微应用QuickStart示例，访问联系人API
 *
 * @author openapi@dingtalk
 * @date 2020/2/4
 */
@Api(tags = "钉钉信息管理Controller")
@RestController
@RequestMapping("/api/dingTalk")
@CrossOrigin("*") // NOTE：此处仅为本地调试使用，为避免安全风险，生产环境请勿设置CORS为 '*'
public class ContactController {
    private static final Set<String> newUserIds = new HashSet<>();
    private static final Logger log = LoggerFactory.getLogger(ContactController.class);
    private final TokenService tokenService;
    private AppConfig appConfig;
    private final DepartmentService departmentService;
    private final DingTalkUserService dingTalkUserService;

    @Autowired
    public ContactController(TokenService tokenService, AppConfig appConfig, DepartmentService departmentService, DingTalkUserService dingTalkUserService) {
        this.tokenService = tokenService;
        this.appConfig = appConfig;
        this.departmentService = departmentService;
        this.dingTalkUserService = dingTalkUserService;
    }

    @ApiOperation(value = "刷新部门")
    @Scheduled(cron = "0 0 9 * * ?")
    @RequestMapping("/department/flushList")
    public RestResponse<List<DepartmentDTO>> listDepartment() {
        String accessToken;
        // 获取accessToken
        ServiceResult<String> accessTokenSr = tokenService.getAccessToken();
        if (!accessTokenSr.isSuccess()) {
            return RestResponse.fail(Integer.parseInt(accessTokenSr.getCode()), accessTokenSr.getMessage());
        }
        accessToken = accessTokenSr.getResult();

        DingTalkClient client = new DefaultDingTalkClient(URL_DEPARTMENT_LIST);
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId("1");
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response;
        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            log.error("Failed to {}", URL_DEPARTMENT_LIST, e);
            return RestResponse.fail(Integer.parseInt(e.getErrCode()), "Failed to listDepartment: " + e.getErrMsg());
        }
        if (!response.isSuccess()) {
            return RestResponse.fail(Integer.parseInt(response.getErrorCode()), response.getErrmsg());
        }

        if (CollectionUtils.isNotEmpty(response.getDepartment())) {
            for (Department department : response.getDepartment()) {
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setId(department.getId());
                departmentDTO.setName(department.getName());
                departmentDTO.setCreateDeptGroup(department.getCreateDeptGroup());
                departmentDTO.setAutoAddUser(department.getAutoAddUser());
                departmentDTO.setParentid(department.getParentid());
                if (departmentService.getDepartmentById(departmentDTO.getId()) == null) {
                    departmentService.insertDepartment(departmentDTO);
                } else {
                    departmentService.updateById(departmentDTO);
                }
            }
            return RestResponse.ok(departmentService.getAllDepartments());
        }
        return RestResponse.ok(Collections.emptyList());
    }

    @ApiOperation(value = "刷新部门用户")
    @Scheduled(cron = "0 30 9 * * ?")
    @RequestMapping("/user/flushList")
    public RestResponse<List<UserDTO>> listDepartmentUsers() {
        String accessToken;
        // 获取accessToken
        ServiceResult<String> accessTokenSr = tokenService.getAccessToken();
        if (!accessTokenSr.isSuccess()) {
            return RestResponse.fail(Integer.parseInt(accessTokenSr.getCode()), accessTokenSr.getMessage());
        }
        accessToken = accessTokenSr.getResult();

        DingTalkClient client = new DefaultDingTalkClient(URL_USER_LIST);
        OapiV2UserListRequest request = new OapiV2UserListRequest();
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        for (DepartmentDTO department : departments) {
            request.setDeptId(department.getId());
        }
        request.setCursor(0L);
        request.setSize(50L);
        request.setHttpMethod("POST");
        OapiV2UserListResponse response;
        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            log.error("Failed to {}", URL_DEPARTMENT_LIST, e);
            return RestResponse.fail(Integer.parseInt(e.getErrCode()), "Failed to listDepartment: " + e.getErrMsg());
        }
        if (!response.isSuccess()) {
            return RestResponse.fail(Integer.parseInt(response.getErrorCode()), response.getErrmsg());
        }

        if (CollectionUtils.isNotEmpty(response.getResult().getList())) {
            for (OapiV2UserListResponse.ListUserResponse userList : response.getResult().getList()) {
                UserDTO user = new UserDTO();
                user.setUserid(userList.getUserid());
                user.setName(userList.getName());
                user.setJobNumber(userList.getJobNumber());
                user.setAvatar(userList.getAvatar());
                user.setHiredDate(userList.getHiredDate());
                user.setUnionId(userList.getUnionid());
                user.setMobile(userList.getMobile());
                user.setTel(userList.getTelephone());
                user.setEmail(userList.getEmail());
                user.setOrgEmail(userList.getOrgEmail());
                user.setWorkPlace(userList.getWorkPlace());
                user.setPosition(userList.getTitle());
                user.setRemark(userList.getRemark());
                user.setActive(userList.getActive());
                user.setAdmin(userList.getAdmin());
                user.setBoss(userList.getBoss());
                user.setHide(userList.getHideMobile());
                user.setLeader(userList.getLeader());
                if (dingTalkUserService.getUserById(user.getUserid()) != null) {
                    dingTalkUserService.updateByPrimaryKey(user);
                } else {
                    newUserIds.add(user.getUserid());
                    dingTalkUserService.insertUser(user);
                }
            }
            return RestResponse.ok(dingTalkUserService.getAllUser());
        }
        return RestResponse.ok(Collections.emptyList());
    }

    @ApiOperation("创建钉钉场景群")
    @GetMapping("/group/createGroup")
    public void createDingTalkGroup() {
        String accessToken;
        ServiceResult<String> accessTokenSr = tokenService.getAccessToken();
        accessToken = accessTokenSr.getResult();
        DingTalkClient client = new DefaultDingTalkClient(URL_CREATE_GROUP);
        OapiImChatScenegroupCreateRequest req = new OapiImChatScenegroupCreateRequest();
        req.setOwnerUserId("311632555536685214");
        req.setMentionAllAuthority(0L);
        req.setShowHistoryType(0L);
        req.setValidationType(0L);
        req.setSearchable(0L);
        req.setChatBannedType(0L);
        req.setManagementType(0L);
        req.setTitle("凌志学习资料群");
        req.setTemplateId(TEMPLATE_ID);
        OapiImChatScenegroupCreateResponse rsp = null;
        try {
            rsp = client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        System.out.println(rsp.getBody());
    }

    @ApiOperation("增加场景群人员")
    @Scheduled(cron = "0 0 10 1 * ?")
    @GetMapping("/group/addUser")
    public void addUserToGroup() {
        String accessToken;
        ServiceResult<String> accessTokenSr = tokenService.getAccessToken();
        accessToken = accessTokenSr.getResult();
        DingTalkClient client = new DefaultDingTalkClient(URL_ADD_USER_TO_GROUP);
        OapiImChatScenegroupMemberAddRequest req = new OapiImChatScenegroupMemberAddRequest();
        req.setOpenConversationId(OPEN_CONVERSATION_ID);
        StringBuilder ids = new StringBuilder();
        for (String id : newUserIds) {
            ids.append(id).append(",");
        }
        ids.deleteCharAt(ids.length() - 1);
        OapiImChatScenegroupMemberAddResponse rsp = null;
        try {
            rsp = client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        System.out.println(rsp.getBody());
        newUserIds.removeAll(newUserIds);
    }

    @ApiOperation(value = "删除场景群人员")
    @Scheduled(cron = "0 0 10 28-31 * ?")
    @GetMapping("/group/deleteUser")
    public void deleteUserInGroup() {
        String accessToken;
        ServiceResult<String> accessTokenSr = tokenService.getAccessToken();
        accessToken = accessTokenSr.getResult();
        DingTalkClient client = new DefaultDingTalkClient(URL_DELETE_USER_IN_GROUP);
        OapiImChatScenegroupMemberDeleteRequest req = new OapiImChatScenegroupMemberDeleteRequest();
        req.setOpenConversationId(OPEN_CONVERSATION_ID);
        StringBuilder ids = new StringBuilder();
        for (String id : newUserIds) {
            ids.append(id).append(",");
        }
        ids.deleteCharAt(ids.length() - 1);
        req.setUserIds(String.valueOf(ids));
        OapiImChatScenegroupMemberDeleteResponse rsp = null;
        try {
            rsp = client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        System.out.println(rsp.getBody());
        newUserIds.removeAll(newUserIds);
    }

    @GetMapping("/group/sendMessage")
    public void sendMessageToGroup() {
        String accessToken;
        ServiceResult<String> accessTokenSr = tokenService.getAccessToken();
        accessToken = accessTokenSr.getResult();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/im/chat/scencegroup/message/send_v2");
        OapiImChatScencegroupMessageSendV2Request req = new OapiImChatScencegroupMessageSendV2Request();
        req.setTargetOpenConversationId(OPEN_CONVERSATION_ID);
        req.setMsgTemplateId("inner_app_template_markdown");
        req.setIsAtAll(true);
        req.setMsgParamMap("{\"title\":\"测试\",\"markdown_content\":\"# 测试内容 \n @180xxxx3120\"}");
        req.setRobotCode("MZNmzneTfXjI2w216659981012831106");
        OapiImChatScencegroupMessageSendV2Response rsp = null;
        try {
            rsp = client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        System.out.println(rsp.getBody());
    }

    @ApiOperation("获取钉钉部门用户列表")
    @RequestMapping("/user/list")
    public RestResponse<List<UserDTO>> dingTalkUserList() {
        List<UserDTO> user = dingTalkUserService.getAllUser();
        return RestResponse.ok(user);
    }
}
