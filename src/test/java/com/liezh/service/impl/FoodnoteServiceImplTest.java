package com.liezh.service.impl;

import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.entity.Foodnote;
import com.liezh.service.IFoodnoteService;
import com.liezh.utils.JsonUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/2/28.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class FoodnoteServiceImplTest {

    @Autowired
    private IFoodnoteService foodnoteService;

    @Test
    public void queryFoodnote() throws Exception {
        ServerResponse sp = foodnoteService.queryFoodnote(null,null, 1, 20);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void queryFoodnoteById() throws Exception {
        ServerResponse sp = foodnoteService.queryFoodnoteById(null, 1L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void insertFoodnote() throws Exception {
        Foodnote foodnote = new Foodnote();
        foodnote.setTitle("豆花");
        foodnote.setContent("一碗好的豆花，需要的是耐心，还有好的原材料和卫生");
        foodnote.setAuthorId(1L);
        ServerResponse sp = foodnoteService.insertFoodnote(foodnote);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void updateFoodnote() throws Exception {
        Foodnote foodnote = new Foodnote();
        foodnote.setId(1L);
        foodnote.setTitle("馄饨");
        foodnote.setAuthorId(1L);
        ServerResponse sp = foodnoteService.updateFoodnote(foodnote);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void deleteFoodnote() throws Exception {
        ServerResponse sp = foodnoteService.deleteFoodnote(1L, 3L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void releaseFoodnote() throws Exception {
        ServerResponse sp = foodnoteService.releaseFoodnote(1L, 2L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void good() throws Exception {
        ServerResponse sp = foodnoteService.good(1L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

}