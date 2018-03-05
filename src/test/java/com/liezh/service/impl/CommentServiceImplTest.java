package com.liezh.service.impl;

import com.liezh.domain.constant.CommentTargetTypeEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.comment.CommentQuery;
import com.liezh.domain.entity.Comment;
import com.liezh.service.ICommentService;
import com.liezh.utils.JsonUtil;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/2/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class CommentServiceImplTest {

    @Autowired
    private ICommentService commentService;

    @Test
    public void queryComment() throws Exception {
        CommentQuery query = new CommentQuery();
        ServerResponse sp = commentService.queryComment(query, 1, 20);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void insertComment() throws Exception {
        Comment comment = new Comment();
        comment.setTargetId(1L);
        comment.setTargetType(CommentTargetTypeEnum.TARGET_RECIPE.getCode());
        comment.setPublisherId(1L);
        comment.setContent("看起开很好吃啊！！！！");
        ServerResponse sp = commentService.insertComment(comment);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void deleteComment() throws Exception {
        ServerResponse sp = commentService.deleteComment(1L, 2L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

    @Test
    public void good() throws Exception {
        ServerResponse sp = commentService.good(1L);
        System.out.println(JsonUtil.toJson(sp));
        assert sp.isSuccess();
    }

}