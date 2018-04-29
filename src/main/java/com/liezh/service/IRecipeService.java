package com.liezh.service;

import com.github.pagehelper.PageInfo;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.recipe.RecipeInfoDto;
import com.liezh.domain.dto.recipe.RecipeInsertDto;
import com.liezh.domain.dto.recipe.RecipeQueryDto;
import com.liezh.domain.dto.recipe.RecipeUpdateDto;

import java.util.List;

/**
 * Created by Administrator on 2018/2/17.
 */
public interface IRecipeService {

    ServerResponse<PageInfo> queryRecipe(Long myId, RecipeQueryDto query, Integer pageNum, Integer pageSize);

    ServerResponse<RecipeInfoDto> queryRecipeById(Long myId, Long recipeId);

    ServerResponse<Long> insertRecipe(RecipeInsertDto recipeInsertDto);

    ServerResponse<Integer> updateRecipe(RecipeUpdateDto recipeUpdateDto);

    ServerResponse<Integer> deleteRecipe(Long myId, Long recipeId);

    ServerResponse<PageInfo> getSubjectByRecipeId(Long recipeId, Integer pageNum, Integer pageSize);

    ServerResponse<Integer> releaseRecipe(Long myId, Long recipeId);

    ServerResponse<Integer> good(Long recipeId);

    ServerResponse<List> queryTopRecipeTitle(String query, Integer size);
}
