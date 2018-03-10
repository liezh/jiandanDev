package com.liezh.dao;

import com.liezh.domain.dto.recipe.RecipeInfoDto;
import com.liezh.domain.dto.subject.SubjectInfoDto;
import com.liezh.domain.entity.Recipe;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/15.
 */
@Repository
public interface RecipeDao {

    List<RecipeInfoDto> queryRecipe(Recipe recipe);

    RecipeInfoDto queryRecipeById(Long recipeId);

    Integer insertRecipe(Recipe recipe);

    Integer updateRecipe(Recipe recipe);

    Integer deleteRecipe(Long recipeId);

    Integer countRecipeById(@Param("userId") Long userId, @Param("recipeId") Long recipeId);

    List<SubjectInfoDto> getSubjectByRecipeId(Long recipeId);

    Integer updateRecipeReadCount(Long recipeId);

    Integer updateRecipeGoodCount(Long recipeId);

    Set<String> queryTopRecipeTile(String query);
}
