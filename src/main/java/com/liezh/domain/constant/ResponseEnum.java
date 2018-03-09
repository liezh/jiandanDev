package com.liezh.domain.constant;

/**
 * Created by Administrator on 2017/10/2.
 *  响应码  --  消息   枚举
 */
public enum ResponseEnum {

    SUCCESS(1000, "成功"),
    ERROR(10001, "未知错误"),

    SERVER_ERROR(10002, "服务异常"),
    RECORD_NOT_FOUND(10003, "记录不存在"),
    RECORD_EXIST(10004, "记录已存在"),

    INSERT_FAILURE(10005, "添加失败"),
    UPDATE_FAILURE(10006, "更新失败"),
    DELETE_FAILURE(10007, "删除失败"),
    QUERY_FAILURE(10008, "查询失败"),

    AUTH_REFRESH_FAILURE(10009, "token更新失败"),

    OPERATION_DISABLE(10009, "操作禁用"),
    ILLEGAL_ARGUMENT(10010, "参数错误"),

    FILE_UPLOAD_FAILURE(10011, "文件上传失败"),

    USER_ACCOUNT_EXIST(20001, "账号名已经注册"),
    USER_MOBILE_EXIST(20002, "手机号已注册"),
    USER_EMAIL_EXIST(20003, "邮箱已注册"),
    USER_PASSWORD_ILLEGAL(20004, "用户密码验证出错"),
    USER_TARGET_NOT_FOUND(20005, "目标用户不存在"),
    USER_TARGET_EXIST(20006, "目标用户已关注"),
    USER_TARGET_IS_SELF(20007, "目标用户不能是自己"),
    USER_FOLLOW_FAILURE(20008, "关注失败"),
    USER_UNFOLLOW_FAILURE(20009, "取消关注失败"),
    USER_TOKEN_TIMEOUT(20010, "密码token过期"),
    USER_ROLE_MAPPING_FAILURE(20011, "用户角色映射失败"),
    USER_ACCOUNT_PASSWORD_ERROR(20012, "用户名或密码错误"),

    ROLE__FAILURE(30001, "角色初始化失败"),
    ROLE_NOT_FOUND(30002, "角色不存在"),
    ROLE_NAME_EXIST(30003, "角色名已存在"),

    SUBJECT_NOT_FOUND(50001, "主题菜单不存在"),
    SUBJECT_CONTRIBUTE_FAILURE(50002, "投稿失败"),
    SUBJECT_MAX_CONTRIBUTE(50003, "投稿次数过多"),
    SUBJECT_CONTRIBUTE_RECORD_NOT_FOUND(50004, "没有待通过投稿记录"),
    SUBJECT_CONTRIBUTE_PASS_FAILURE(50005, "主题菜单投稿审核通过失败"),
    SUBJECT_CONTRIBUTE_REJECT_FAILURE(50006, "主题菜单投搞审核拒绝失败"),
    SUBJECT_COLLECT_FAILURE(50007, "主题菜单收藏失败"),
    SUBJECT_UNCOLLECT_FAILURE(50008, "主题菜单取消收藏失败"),

    RECIPE_NOT_FOUND(60001, "菜谱不存在"),
    RECIPE_INSERT_FAILURE(60002, "菜谱添加失败"),
    RECIPE_UPDATE_FAILURE(60003, "菜谱修改失败"),
    RECIPE_DELETE_FAILURE(60004, "菜谱删除失败"),
    RECIPE_RELEASE_FAILURE(60005, "菜谱发表失败"),
    RECIPE_GOOD_FAILURE(60006, "菜谱点赞失败"),

    FOODNOTE_NOT_FOUND(70001, "食记不存在"),
    FOODNOTE_RELEASE_FAILURE(70002, "食记发表失败"),
    FOODNOTE_GOOD_FAILURE(70003, "食记点赞失败"),


    COMMENT_NOT_FOUND(80001, "评论不存在"),
    COMMENT_TARGET_TYPE_UNKNOWN(80002, "评论目标类型不存在"),
    COMMENT_GOOD_FAILURE(80003, "评论点赞失败"),
    COMMENT_TARGET_NOT_FOUND(80004, "评论目标不存在"),


    NEED_LOGIN(90000, "需要登录"),
    PERMISSION_DENIED(90001, "权限不足"),

    ;


    private final  int code;
    private final  String desc;

    ResponseEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
