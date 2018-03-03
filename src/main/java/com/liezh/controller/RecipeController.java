package com.liezh.controller;

import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.recipe.RecipeInsertDto;
import com.liezh.domain.dto.recipe.RecipeQueryDto;
import com.liezh.domain.dto.recipe.RecipeUpdateDto;
import com.liezh.domain.entity.Recipe;
import com.liezh.service.IRecipeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/3.
 */
@RestController
@RequestMapping("/api/r")
public class RecipeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    private IRecipeService recipeService;

    @GetMapping("/search")
    public ServerResponse searchRecipe(@RequestParam(required = false) String query,
                                       @RequestParam(required = false) Integer pageNum,
                                       @RequestParam(required = false) Integer pageSize) {
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        RecipeQueryDto recipeQueryDto = new RecipeQueryDto();
        recipeQueryDto.setStatus(GlobalConstants.STATUS_RELEASE);
        recipeQueryDto.setTitle(query);
        Long myId = getLoginUserId();
        return recipeService.queryRecipe(myId, recipeQueryDto, pageNum, pageSize);
    }

    /**
     *  发现菜谱
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    public ServerResponse getAllRecipe(@RequestParam(required = false) Integer pageNum,
                                       @RequestParam(required = false) Integer pageSize) {
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        Long myId = getLoginUserId();
        return recipeService.queryRecipe(myId, null, pageNum, pageSize);
    }

    /**
     *  获取菜谱详情
     * @return
     */
    @GetMapping("/{rid}")
    public ServerResponse getRecipeById(@PathVariable("/rid") Long rid) {
        if (rid == null) {
            logger.error("菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getLoginUserId();
        return recipeService.queryRecipeById(myId, rid);
    }

    @PostMapping
    public ServerResponse insertRecipe(RecipeInsertDto recipeInsertDto) {
        if (recipeInsertDto == null || StringUtils.isBlank(recipeInsertDto.getTitle())) {
            logger.error("菜谱标题为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        return recipeService.insertRecipe(recipeInsertDto);
    }

    @PutMapping
    public ServerResponse updateRecipe(RecipeUpdateDto recipeUpdateDto) {
        if (recipeUpdateDto == null || recipeUpdateDto.getId() == null) {
            logger.error("菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        return recipeService.updateRecipe(recipeUpdateDto);
    }

    @DeleteMapping("/{rid}")
    public ServerResponse deleteRecipe(@PathVariable("rid") Long rid) {
        if (rid == null) {
            logger.error("菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getLoginUserId();
        return recipeService.deleteRecipe(myId, rid);
    }

    @GetMapping("/{rid}/subject")
    public ServerResponse getSubjectByRecipeId(@PathVariable("rid") Long rid,
                                               @RequestParam(required = false) Integer pageNum,
                                               @RequestParam(required = false) Integer pageSize) {
        if (rid == null) {
            logger.error("菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
//        Long myId = getLoginUserId();
        return recipeService.getSubjectByRecipeId(rid, pageNum, pageSize);
    }

    @PutMapping("/release/{rid}")
    public ServerResponse releaseRecipe(@PathVariable("rid") Long rid) {
        if (rid == null) {
            logger.error("菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getLoginUserId();
        return recipeService.releaseRecipe(myId, rid);
    }

    @PutMapping("/{rid}/good")
    public ServerResponse good(@PathVariable("rid") Long rid) {
        if (rid == null) {
            logger.error("菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        return recipeService.good(rid);
    }

}
