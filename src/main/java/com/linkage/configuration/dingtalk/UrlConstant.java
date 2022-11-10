package com.linkage.configuration.dingtalk;

/**
 * top地址相关配置
 *
 * @author openapi@dingtalk
 * @date 2020/2/4
 */
public class UrlConstant {
    private static final String HOST = "https://oapi.dingtalk.com";

    /**
     * 钉钉网关 gettoken 地址
     */
    public static final String URL_GET_TOKEN = HOST + "/gettoken";

    /**
     * 获取 jsapi_ticket 地址
     */
    public static final String URL_GET_JSTICKET = HOST + "/get_jsapi_ticket";

    /**
     * 获取用户在企业内 userId 的接口URL
     */
    public static final String URL_GET_USER_INFO = HOST + "/user/getuserinfo";

    /**
     * 获取用户姓名的接口URL
     */
    public static final String URL_USER_GET = HOST + "/user/get";

    /**
     * 获取部门列表接口URL
     */
    public static final String URL_DEPARTMENT_LIST = HOST + "/department/list";

    /**
     * 获取部门用户接口URL
     */
    public static final String URL_USER_SIMPLELIST = HOST + "/user/simplelist";

    /**
     * 获取部门用户详情
     */
    public static final String URL_USER_LIST = HOST + "/topapi/v2/user/list";

    /**
     * 创建场景群
     */
    public static final String URL_CREATE_GROUP = HOST + "/topapi/im/chat/scenegroup/create";

    /**
     * 新增场景群人员
     */
    public static final String URL_ADD_USER_TO_GROUP = HOST + "/topapi/im/chat/scenegroup/member/add";

    /**
     * 删除场景群人员
     */
    public static final String URL_DELETE_USER_IN_GROUP = HOST + "/topapi/im/chat/scenegroup/member/delete";

    /**
     * 场景群ID
     */
    public static final String OPEN_CONVERSATION_ID = "cidN4Gc98Z\\/IIp4P4MoDN50NQ==";

    /**
     * 场景群模板ID
     */
    public static final String TEMPLATE_ID = "688bd1a9-8d4b-4ab4-8ddf-6a3005149f5e";

}
