package com.liezh.domain.dto.recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/24.
 */
public class RecipeInsertDto {

    private String title;

    private String cover;

    private String content;

    private String synopsis;

    private Float heat;

    private Integer status;

    // 作者信息
    private Long authorId;

    private List<ProcessDto> process = new ArrayList<>();

    private List<MaterialDto> materials = new ArrayList<>();

    public RecipeInsertDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public List<ProcessDto> getProcess() {
        return process;
    }

    public void setProcess(List<ProcessDto> process) {
        this.process = process;
    }

    public List<MaterialDto> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialDto> materials) {
        this.materials = materials;
    }
}
