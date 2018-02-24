package com.liezh.domain.dto.recipe;

/**
 * Created by Administrator on 2018/2/24.
 */
public class RecipeUpdateDto extends RecipeInsertDto {

    private Long id;

    public RecipeUpdateDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
