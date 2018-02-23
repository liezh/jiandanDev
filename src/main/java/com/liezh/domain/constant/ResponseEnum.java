package com.liezh.domain.constant;

/**
 * Created by Administrator on 2017/10/2.
 *  响应码  --  消息   枚举
 */
public enum ResponseCodeEnum {

    SUCCESS(1000, "成功"),
    ERROR(10001, "未知错误"),

    SERVER_ERROR(10002, "服务异常"),
    RECORD_NOT_FOUND(10003, "记录不存在"),
    RECORD_EXIST(10004, "记录已存在"),

    INSERT_FAILURE(10005, "添加失败"),
    UPDATE_FAILURE(10006, "更新失败"),
    DELETE_FAILURE(10007, "删除失败"),
    QUERY_FAILURE(10008, "查询失败"),

    OPERATION_DISABLE(10009, "操作禁用"),
    ILLEGAL_ARGUMENT(10010, "参数错误"),


    NEED_LOGIN(90000, "需要登录"),
    PERMISSION_DENIED(90001, "权限不足"),

    ;


    private final  int code;
    private final  String desc;

    ResponseCodeEnum(int code, String desc) {
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
