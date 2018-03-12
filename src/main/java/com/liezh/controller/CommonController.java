package com.liezh.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.recipe.RecipeInfoDto;
import com.liezh.domain.dto.recipe.RecipeQueryDto;
import com.liezh.service.IRecipeService;
import com.liezh.service.ISubjectService;
import com.liezh.service.IUserService;
import groovy.transform.BaseScript;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/10.
 */
@RestController
@RequestMapping("/api")
public class CommonController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private ISubjectService subjectService;

    @Autowired
    private IRecipeService recipeService;


    @GetMapping("/index")
    public ServerResponse index() {
        Long myId = getAuthUserId();
        RecipeQueryDto rq = new RecipeQueryDto();
        rq.setStatus(GlobalConstants.STATUS_RELEASE);
        ServerResponse rSP= recipeService.queryRecipe(myId, rq, GlobalConstants.PAGE_NUM, 9);
        ServerResponse sSP = subjectService.querySubject(myId, null, GlobalConstants.PAGE_NUM, 9);
        ServerResponse hSP = recipeService.queryRecipe(myId, rq, GlobalConstants.PAGE_NUM, 6);
        Map resultMap = Maps.newTreeMap();
        resultMap.put("headlines", hSP.getData());
        resultMap.put("hotRecipes", rSP.getData());
        resultMap.put("hotSubject", sSP.getData());
        return ServerResponse.createBySuccess(resultMap);
    }

    @GetMapping("/autocomplete")
    public ServerResponse autocomplete(@RequestParam("query") String query) {
        if (StringUtils.isBlank(query)) {
            logger.error("查询条件为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        RecipeQueryDto recipeQueryDto = new RecipeQueryDto();
        recipeQueryDto.setTitle(query);
        ServerResponse sp = recipeService.queryTopRecipeTitle(query, 10);

        List resultList = Lists.newArrayList();
        if (sp.isSuccess()) {
            for (String item : (List<String>) sp.getData() ) {
                Map map = Maps.newHashMap();
                map.put("value", item);
                map.put("address", "123");
                resultList.add(map);
            }
        }
        return ServerResponse.createBySuccess(resultList);
    }



}
