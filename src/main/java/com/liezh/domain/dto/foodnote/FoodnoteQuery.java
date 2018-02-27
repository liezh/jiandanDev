package com.liezh.domain.dto.foodnote;

/**
 * Created by Administrator on 2018/2/26.
 */
public class FoodnoteQuery {

    private String title;

    private Integer status;

    private Long authorId;

    public FoodnoteQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
