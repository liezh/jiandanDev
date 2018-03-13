package com.liezh.controller;

import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.recipe.RecipeInsertDto;
import com.liezh.domain.dto.recipe.RecipeQueryDto;
import com.liezh.domain.dto.recipe.RecipeUpdateDto;
import com.liezh.service.IRecipeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        Long myId = getAuthUserId();
        return recipeService.queryRecipe(myId, recipeQueryDto, pageNum, pageSize);
    }


    @GetMapping("/my/releases")
    @PreAuthorize("authenticated")
    public ServerResponse getMyRecipesByRelease(@RequestParam(required = false) Integer pageNum,
                                       @RequestParam(required = false) Integer pageSize) {
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        Long myId = getAuthUserId();
        RecipeQueryDto recipeQueryDto = new RecipeQueryDto();
        recipeQueryDto.setStatus(GlobalConstants.STATUS_RELEASE);
        recipeQueryDto.setAuthorId(myId);
        return recipeService.queryRecipe(myId, recipeQueryDto, pageNum, pageSize);
    }

    @GetMapping("/{uid}/list")
    @PreAuthorize("authenticated")
    public ServerResponse getRecipesByUserId(@PathVariable("uid") Long uid,
                                             @RequestParam(required = false) Integer pageNum,
                                          @RequestParam(required = false) Integer pageSize) {
        if (uid == null) {
            logger.error("用户id作者id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        Long myId = getAuthUserId();
        RecipeQueryDto recipeQueryDto = new RecipeQueryDto();
        if (myId == null || uid != myId) {
            recipeQueryDto.setStatus(GlobalConstants.STATUS_RELEASE);
        }
        recipeQueryDto.setAuthorId(myId);
        return recipeService.queryRecipe(myId, recipeQueryDto, pageNum, pageSize);
    }


    /**
     *  获取菜谱详情
     * @return
     */
    @GetMapping("/{rid}")
    public ServerResponse getRecipeById(@PathVariable("rid") Long rid) {
        if (rid == null) {
            logger.error("菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getAuthUserId();
        return recipeService.queryRecipeById(myId, rid);
    }

    @PostMapping
    @PreAuthorize("authenticated")
    public ServerResponse insertRecipe(@RequestBody RecipeInsertDto recipeInsertDto) {
        if (recipeInsertDto == null || StringUtils.isBlank(recipeInsertDto.getTitle())) {
            logger.error("菜谱标题为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getAuthUserId();
        recipeInsertDto.setAuthorId(myId);
        return recipeService.insertRecipe(recipeInsertDto);
    }

    @PutMapping
    @PreAuthorize("authenticated")
    public ServerResponse updateRecipe(@RequestBody RecipeUpdateDto recipeUpdateDto) {
        if (recipeUpdateDto == null || recipeUpdateDto.getId() == null) {
            logger.error("菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getAuthUserId();
        recipeUpdateDto.setAuthorId(myId);
        return recipeService.updateRecipe(recipeUpdateDto);
    }

    @DeleteMapping("/{rid}")
    @PreAuthorize("authenticated")
    public ServerResponse deleteRecipe(@PathVariable("rid") Long rid) {
        if (rid == null) {
            logger.error("菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getAuthUserId();
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
//        Long myId = getAuthUserId();
        return recipeService.getSubjectByRecipeId(rid, pageNum, pageSize);
    }

    @PostMapping("/release")
    @PreAuthorize("authenticated")
    public ServerResponse releaseAndSaveRecipe(@RequestBody RecipeUpdateDto recipeUpdateDto) {

        Long myId = getAuthUserId();
        recipeUpdateDto.setAuthorId(myId);
        recipeUpdateDto.setStatus(GlobalConstants.STATUS_RELEASE);
        if (recipeUpdateDto.getId() == null) {
            return recipeService.insertRecipe(recipeUpdateDto);
        }
        return recipeService.updateRecipe(recipeUpdateDto);
    }

    @PutMapping("/release/{rid}")
    @PreAuthorize("authenticated")
    public ServerResponse releaseRecipe(@PathVariable("rid") Long rid) {
        if (rid == null) {
            logger.error("菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getAuthUserId();
        return recipeService.releaseRecipe(myId, rid);
    }

    @PutMapping("/{rid}/good")
    @PreAuthorize("authenticated")
    public ServerResponse good(@PathVariable("rid") Long rid) {
        if (rid == null) {
            logger.error("菜谱id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        return recipeService.good(rid);
    }

}
