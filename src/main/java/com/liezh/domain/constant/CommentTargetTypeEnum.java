package com.liezh.domain.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2018/2/17.
 */
public enum CommentTargetTypeEnum {

    TARGET_RECIPE("recipe", "菜谱评论"),

    TARGET_FOODNOTE("foodnote", "食记评论"),

    ;


    private String code;

    private String desc;

    CommentTargetTypeEnum() {
    }

    CommentTargetTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static final boolean isMatch(String type) {
        if (StringUtils.isBlank(type)) {
            return false;
        }
        for (CommentTargetTypeEnum item : CommentTargetTypeEnum.values()) {
            if (StringUtils.equals(item.getCode(), type)) {
                return true;
            }
        }
        return false;
    }

}
