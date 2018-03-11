package com.liezh.controller;

import com.liezh.domain.constant.CommentTargetTypeEnum;
import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.comment.CommentQuery;
import com.liezh.domain.entity.Comment;
import com.liezh.service.ICommentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/3/11.
 */
@RestController
@RequestMapping("/api/c")
public class CommentController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private ICommentService commentService;

    @PostMapping("/recipe")
    public ServerResponse insertRecipeComment(@RequestBody Comment comment) {
        if (comment == null || StringUtils.isBlank(comment.getContent()) || comment.getTargetId() == null) {
            logger.error("评论内容或目标id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getAuthUserId();
        comment.setPublisherId(myId);
        comment.setTargetType(CommentTargetTypeEnum.TARGET_RECIPE.getCode());
        return commentService.insertComment(comment);
    }

    @PostMapping("/foodnote")
    public ServerResponse insertFoodnoteComment(Comment comment) {
        if (comment == null || StringUtils.isBlank(comment.getContent()) || comment.getTargetId() == null) {
            logger.error("评论内容或目标id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getAuthUserId();
        comment.setPublisherId(myId);
        comment.setTargetType(CommentTargetTypeEnum.TARGET_FOODNOTE.getCode());
        return commentService.insertComment(comment);
    }

    @GetMapping("/{tid}")
    public ServerResponse getCommentByTargetId(@PathVariable("tid") Long tid,
                                               @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                               @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (tid == null) {
            logger.error("目标id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        CommentQuery commentQuery = new CommentQuery();
        commentQuery.setTargetId(tid);
        return commentService.queryComment(commentQuery, pageNum, pageSize);
    }

    /**
     *  获取自己的评论
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    public ServerResponse getCommentByPublisherId(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                               @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        Long myId = getAuthUserId();
        CommentQuery commentQuery = new CommentQuery();
        commentQuery.setPublisherId(myId);
        return commentService.queryComment(commentQuery, pageNum, pageSize);
    }

    @DeleteMapping("/{cid}")
    public ServerResponse deleteComment(@PathVariable("cid") Long cid) {
        if (cid == null) {
            logger.error("评论记录id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getAuthUserId();
        return commentService.deleteComment(myId, cid);
    }

    @PutMapping("/{cid}/good")
    public ServerResponse good(@PathVariable("cid") Long cid) {
        if (cid == null) {
            logger.error("评论记录id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        return commentService.good(cid);
    }



}
