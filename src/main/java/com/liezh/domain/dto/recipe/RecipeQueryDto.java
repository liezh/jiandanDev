package com.liezh.domain.dto.recipe;

/**
 * Created by Administrator on 2018/2/23.
 */
public class RecipeQueryDto {

    private String title;

    private Float heat;

    private Integer status;

    private String materials;

    private Long authorId;

    public RecipeQueryDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getHeat() {
        return heat;
    }

    public void setHeat(Float heat) {
        this.heat = heat;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
