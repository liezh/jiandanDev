package com.liezh.domain.dto.comment;

/**
 * Created by Administrator on 2018/2/26.
 */
public class CommentQuery {

    private Long publisherId;

    private String targetType;

    private Long targetId;

    public CommentQuery() {
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }
}
