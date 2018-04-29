package com.liezh.service.impl;

import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.recipe.MaterialDto;
import com.liezh.domain.dto.recipe.ProcessDto;
import com.liezh.domain.dto.recipe.RecipeInsertDto;
import com.liezh.domain.dto.recipe.RecipeUpdateDto;
import com.liezh.service.IRecipeService;
import com.liezh.utils.JsonUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/2/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class RecipeServiceImplTest {

    @Autowired
    private IRecipeService recipeService;

    @Test
    public void queryRecipe() throws Exception {
        ServerResponse sp = recipeService.queryRecipe(1L, null, 1, 20);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void queryRecipeById() throws Exception {
        ServerResponse sp = recipeService.queryRecipeById(1L, 1L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void insertRecipe() throws Exception {
        RecipeInsertDto recipeInsertDto = new RecipeInsertDto();
        recipeInsertDto.setTitle("排骨鸡煲");
        recipeInsertDto.setSynopsis("排骨和鸡同时煲就行了");
        recipeInsertDto.setAuthorId(1L);
        recipeInsertDto.setContent("最重要是排骨和鸡的分量比例，还有酱汁的调配");
        recipeInsertDto.setCover("https://i0.hdslb.com/bfs/bangumi/1fbe57ee123f734837d29accbf85bddca2a83973.jpg@72w_72h.webp");

        List<ProcessDto> processDtos = new ArrayList<>();
        ProcessDto processDto1 = new ProcessDto();
        processDto1.setContent("排骨斩件，放下砂锅");
        processDto1.setPicture("https://i0.hdslb.com/bfs/bangumi/efab1c1d824c9f2f7a5b0d091c907093ab9a5e6a.jpg@72w_72h.webp");
        processDto1.setIndex(1);
        processDtos.add(processDto1);
        ProcessDto processDto2 = new ProcessDto();
        processDto2.setContent("整鸡斩件，放下砂锅");
        processDto2.setPicture("https://i0.hdslb.com/bfs/bangumi/cfcf78a10199065acc25a3fdd13b59526cc6ad4d.jpg@72w_72h.webp");
        processDto2.setIndex(2);
        processDtos.add(processDto2);
        ProcessDto processDto3 = new ProcessDto();
        processDto3.setContent("小火慢炖30分钟");
        processDto3.setPicture("https://i0.hdslb.com/bfs/bangumi/3121473d5dd03a9bcccb8490034207e724e731b3.jpg@72w_72h.webp");
        processDto3.setIndex(3);
        processDtos.add(processDto3);
        recipeInsertDto.setProcess(processDtos);

        List<MaterialDto> materialDtos = new ArrayList<>();
        MaterialDto materialDto1 = new MaterialDto();
        materialDto1.setName("排骨");
        materialDto1.setAmount(200f);
        materialDto1.setUnit("克");
        materialDtos.add(materialDto1);
        MaterialDto materialDto2 = new MaterialDto();
        materialDto2.setName("土鸡");
        materialDto2.setAmount(400f);
        materialDto2.setUnit("克");
        materialDtos.add(materialDto2);
        recipeInsertDto.setMaterials(materialDtos);

        ServerResponse sp = recipeService.insertRecipe(recipeInsertDto);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();

    }

    @Test
    public void updateRecipe() throws Exception {
        RecipeUpdateDto recipeUpdateDto = new RecipeUpdateDto();
        recipeUpdateDto.setId(1L);
        recipeUpdateDto.setAuthorId(1L);
        List<ProcessDto> processDtos = new ArrayList<>();
        ProcessDto processDto1 = new ProcessDto();
        processDto1.setContent("放油人锅");
        processDto1.setPicture("https://i0.hdslb.com/bfs/bangumi/efab1c1d824c9f2f7a5b0d091c907093ab9a5e6a.jpg@72w_72h.webp");
        processDto1.setIndex(1);
        processDtos.add(processDto1);
        ProcessDto processDto2 = new ProcessDto();
        processDto2.setContent("下蛋剪，一分钟即可");
        processDto2.setPicture("https://i0.hdslb.com/bfs/bangumi/cfcf78a10199065acc25a3fdd13b59526cc6ad4d.jpg@72w_72h.webp");
        processDto2.setIndex(2);
        processDtos.add(processDto2);
        recipeUpdateDto.setProcess(processDtos);

        List<MaterialDto> materialDtos = new ArrayList<>();
        MaterialDto materialDto1 = new MaterialDto();
        materialDto1.setName("鸡蛋");
        materialDto1.setAmount(1f);
        materialDto1.setUnit("个");
        materialDtos.add(materialDto1);
        recipeUpdateDto.setMaterials(materialDtos);
        ServerResponse sp = recipeService.updateRecipe(recipeUpdateDto);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void deleteRecipe() throws Exception {
        ServerResponse sp = recipeService.deleteRecipe(1L, 4L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void getSubjectByRecipeId() throws Exception {
        ServerResponse sp = recipeService.getSubjectByRecipeId(1L, 1, 20);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void releaseRecipe() throws Exception {
        ServerResponse sp = recipeService.releaseRecipe(1L, 5L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void good() throws Exception {
        ServerResponse sp = recipeService.good(5L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }


}