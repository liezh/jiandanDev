package com.liezh.domain.dto.recipe;

/**
 * Created by Administrator on 2018/2/24.
 */
public class ProcessDto {

    private String content;

    private String picture;

    private Integer index;

    public ProcessDto() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
