package com.liezh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.liezh.dao.RecipeDao;
import com.liezh.dao.UserDao;
import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.recipe.RecipeInfoDto;
import com.liezh.domain.dto.recipe.RecipeInsertDto;
import com.liezh.domain.dto.recipe.RecipeQueryDto;
import com.liezh.domain.dto.recipe.RecipeUpdateDto;
import com.liezh.domain.dto.subject.SubjectInfoDto;
import com.liezh.domain.entity.Recipe;
import com.liezh.service.IRecipeService;
import com.liezh.utils.JsonUtil;
import com.sun.istack.internal.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/23.
 */
@Service
public class RecipeServiceImpl implements IRecipeService {

    private static Logger logger = LoggerFactory.getLogger(RecipeServiceImpl.class);

    @Autowired
    private RecipeDao recipeDao;

    @Autowired
    private UserDao userDao;

    @Override
    public ServerResponse<PageInfo>  queryRecipe(@Nullable Long myId, RecipeQueryDto query, Integer pageNum, Integer pageSize) {
        if (query == null ) {
            query = new RecipeQueryDto();
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        Recipe recipe = new Recipe();
        recipe.setTitle(query.getTitle());
        recipe.setMaterials(query.getMaterials());
        recipe.setAuthorId(query.getAuthorId());
        recipe.setStatus(query.getStatus());
        PageHelper.startPage(pageNum, pageSize);
        List<RecipeInfoDto> recipes = recipeDao.queryRecipe(recipe);
        PageInfo<RecipeInfoDto> pageInfo = new PageInfo<>(recipes);

        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<RecipeInfoDto> queryRecipeById(@Nullable Long myId, Long recipeId) {
        if (recipeId == null) {
            logger.error("菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        RecipeInfoDto recipeInfoDto = recipeDao.queryRecipeById(recipeId);

        // 判断登录人不是作者, 且文章未发布时
        if (( myId == null || !myId.equals(recipeInfoDto.getAuthorId()) ) && recipeInfoDto.getStatus() != GlobalConstants.STATUS_RELEASE) {
            logger.error("非作者不可参看草稿！ recipeInf: {}", JsonUtil.toJson(recipeInfoDto));
            return ServerResponse.createByResponseEnum(ResponseEnum.PERMISSION_DENIED);
        }

        if (recipeInfoDto == null || recipeInfoDto.getId() == null) {
            logger.error("菜谱不存在！ myId: {}, recipeId: {}", myId, recipeId);
            return ServerResponse.createByResponseEnum(ResponseEnum.RECORD_NOT_FOUND);
        }
        if (myId != null) {
            // 设置关注标记
            int resultCount = userDao.countIdolById(myId, recipeInfoDto.getAuthorId());
            recipeInfoDto.setAuthorHasFollow(resultCount > 0);
        } else {
            recipeInfoDto.setAuthorHasFollow(false);
        }
        // 指针阅读量
        recipeDao.updateRecipeReadCount(recipeId);

        logger.info("获取菜谱成功！ recipe: {}", recipeInfoDto);
        return ServerResponse.createBySuccess(recipeInfoDto);
    }

    @Override
    public ServerResponse<Long> insertRecipe(RecipeInsertDto recipeInsertDto) {
        if (recipeInsertDto == null || recipeInsertDto.getTitle() == null || recipeInsertDto.getAuthorId() == null) {
            logger.error("菜谱标题或作者id为空");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }

        Recipe recipe = new Recipe();
        recipe.setTitle(recipeInsertDto.getTitle());
        recipe.setSynopsis(recipeInsertDto.getSynopsis());
        // set process and materials to Json
        String process = JsonUtil.toJson(recipeInsertDto.getProcess());
        recipe.setProcess(process);
        String materials = JsonUtil.toJson(recipeInsertDto.getMaterials());
        recipe.setMaterials(materials);

        recipe.setContent(recipeInsertDto.getContent());
        // new recipe status only be draft
        recipe.setStatus(GlobalConstants.STATUS_DRAFT);
        recipe.setAuthorId(recipeInsertDto.getAuthorId());
        recipe.setCover(recipeInsertDto.getCover());
//        recipe.setGoodCount(recipeInsertDto.getGoodCount());
//        recipe.setReadCount(recipeInsertDto.getReadCount());
        int resultCount = recipeDao.insertRecipe(recipe);
        if (resultCount > 0) {
            logger.info("菜谱添加成功！ recipe: {}", JsonUtil.toJson(recipe));
            return ServerResponse.createBySuccess(recipe.getId());
        }
        logger.error("菜谱添加失败！ recipe: {}", JsonUtil.toJson(recipe));
        return ServerResponse.createByResponseEnum(ResponseEnum.RECIPE_INSERT_FAILURE);
    }

    @Override
    public ServerResponse<Integer> updateRecipe(RecipeUpdateDto recipeUpdateDto) {
        if (recipeUpdateDto == null || recipeUpdateDto.getAuthorId() == null) {
            logger.error("菜谱作者id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        // 判断菜谱是否存在
        int resultCount = recipeDao.countRecipeById(recipeUpdateDto.getAuthorId(), recipeUpdateDto.getId());
        if (resultCount <= 0) {
            logger.error("菜谱该用户不拥有该菜谱！ authorId: {}, recipeId: {}",recipeUpdateDto.getAuthorId(), recipeUpdateDto.getId());
            return ServerResponse.createBySuccess(0);
        }

        Recipe recipe = new Recipe();
        recipe.setId(recipeUpdateDto.getId());
        recipe.setTitle(recipeUpdateDto.getTitle());
        recipe.setContent(recipeUpdateDto.getContent());
        recipe.setCover(recipeUpdateDto.getCover());
        // set process and materials to Json
        String process = JsonUtil.toJson(recipeUpdateDto.getProcess());
        recipe.setProcess(process);
        String materials = JsonUtil.toJson(recipeUpdateDto.getMaterials());
        recipe.setMaterials(materials);

        recipe.setContent(recipeUpdateDto.getContent());
        recipe.setSynopsis(recipeUpdateDto.getSynopsis());

        resultCount = recipeDao.updateRecipe(recipe);
        if (resultCount > 0) {
            logger.info("菜谱信息修改成功！ recipe: {}", JsonUtil.toJson(recipe));
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("菜谱信息修改失败！ recipe: {}", JsonUtil.toJson(recipe));
        return ServerResponse.createByResponseEnum(ResponseEnum.RECIPE_UPDATE_FAILURE);
    }

    @Override
    public ServerResponse<Integer> deleteRecipe(Long myId, Long recipeId) {
        if (myId == null || recipeId == null) {
            logger.error("登录用户id或菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        // 判断菜谱是否存在
        int resultCount = recipeDao.countRecipeById(myId, recipeId);
        if (resultCount <= 0) {
            logger.error("菜谱该用户不拥有该菜谱！ myId: {}, recipeId: {}",myId, recipeId);
            return ServerResponse.createBySuccess(0);
        }
        resultCount = recipeDao.deleteRecipe(recipeId);
        if (resultCount > 0) {
            logger.info("菜谱删除成功！ myId: {}, recipeId: {}", myId, recipeId);
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("菜谱删除失败！ myId: {}, recipe: {}", myId, recipeId);
        return ServerResponse.createByResponseEnum(ResponseEnum.RECIPE_DELETE_FAILURE);
    }

    /**
     *  获取某菜谱，所有投稿并发布了的主题菜单列表
     * @param recipeId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getSubjectByRecipeId(Long recipeId, Integer pageNum, Integer pageSize) {
        if (recipeId == null) {
            logger.error("菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        // 菜谱是否存在
        int rcount = recipeDao.countRecipeById(null, recipeId);
        if (rcount <= 0) {
            logger.error("菜谱不存在！ recipeId: {}", recipeId);
            return ServerResponse.createByResponseEnum(ResponseEnum.RECIPE_NOT_FOUND);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<SubjectInfoDto> subjects = recipeDao.getSubjectByRecipeId(recipeId);
        PageInfo<SubjectInfoDto> pageInfo = new PageInfo<>(subjects);
        logger.info("菜谱成功投稿的主题！ recipeId: {}, subjectList: {}", recipeId, JsonUtil.toJson(pageInfo));
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse<Integer> releaseRecipe(Long myId, Long recipeId) {
        if (myId == null || recipeId == null) {
            logger.error("登录用户id或菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.setStatus(GlobalConstants.STATUS_RELEASE);
        recipe.setReleaseTime(new Date());
        int resultCount = recipeDao.updateRecipe(recipe);
        if (resultCount > 0) {
            logger.info("菜谱发布成功！ myId: {}, recipeId: {}", myId, recipeId);
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("菜谱发布失败！ myId: {}, recipeId: {}", myId, recipeId);
        return ServerResponse.createByResponseEnum(ResponseEnum.RECIPE_RELEASE_FAILURE);
    }

    public ServerResponse<Integer> good(Long recipeId) {
        if (recipeId == null) {
            logger.error("菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        int resultCount = recipeDao.countRecipeById(null, recipeId);
        if (resultCount <= 0) {
            logger.error("菜谱不存在！ recipeId: {}", recipeId);
            return ServerResponse.createByResponseEnum(ResponseEnum.RECORD_NOT_FOUND);
        }
        resultCount = recipeDao.updateRecipeGoodCount(recipeId);
        if (resultCount > 0) {
            logger.info("菜谱点赞成功！ recipeId: {}", recipeId);
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("菜谱点赞失败！ recipeId: {}", recipeId);
        return ServerResponse.createByResponseEnum(ResponseEnum.RECIPE_GOOD_FAILURE);
    }

    public ServerResponse<List> queryTopRecipeTitle(String query, Integer size) {
        if (StringUtils.isBlank(query) || size == null || size <= 0) {
            logger.error("参数错误！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        PageHelper.startPage(GlobalConstants.PAGE_NUM, size);
        Set result = recipeDao.queryTopRecipeTile(query);
        List titleList = Lists.newArrayList(result);
        return ServerResponse.createBySuccess(titleList);
    }

}
