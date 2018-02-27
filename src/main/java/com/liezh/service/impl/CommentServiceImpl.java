package com.liezh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liezh.dao.CommentDao;
import com.liezh.domain.constant.CommentTargetTypeEnum;
import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.comment.CommentInfoDto;
import com.liezh.domain.dto.comment.CommentQuery;
import com.liezh.domain.entity.Comment;
import com.liezh.service.ICommentService;
import com.liezh.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 */
@Service
public class CommentServiceImpl implements ICommentService {

    private static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentDao commentDao;

    @Override
    public ServerResponse<PageInfo> queryComment(CommentQuery query, Integer pageNum, Integer pageSize) {
        if (query == null) {
            query = new CommentQuery();
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        Comment comment = new Comment();
        comment.setPublisherId(query.getPublisherId());
        comment.setTargetType(query.getTargetType());
        comment.setTargetId(query.getTargetId());
        PageHelper.startPage(pageNum, pageSize);
        List<CommentInfoDto> commentInfoDtos = commentDao.queryComment(comment);
        PageInfo pageInfo = new PageInfo(commentInfoDtos);

        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<Long> insertComment(Comment comment) {
        if (comment == null || StringUtils.isBlank(comment.getContent())
                || comment.getPublisherId() == null || comment.getTargetId() == null || StringUtils.isBlank(comment.getTargetType())) {
            logger.error("评论内容，评论发布人id，评论目标id，评论对象类型为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (!CommentTargetTypeEnum.isMatch(comment.getTargetType())) {
            logger.error("评论目标类型不存在");
            return ServerResponse.createByResponseEnum(ResponseEnum.COMMENT_TARGET_TYPE_UNKNOWN);
        }
        // 判断评论目标是否存在
        int resultCount = commentDao.countTarget(comment.getTargetId(), comment.getTargetType());
        if (resultCount <= 0) {
            logger.error("评论目标不存在！ comment: {}", JsonUtil.toJson(comment));
            return ServerResponse.createByResponseEnum(ResponseEnum.COMMENT_TARGET_NOT_FOUND);
        }
        resultCount = commentDao.insertComment(comment);
        if (resultCount > 0) {
            logger.info("评论成功！ comment: {}", JsonUtil.toJson(comment));
            return ServerResponse.createBySuccess(comment.getId());
        }
        logger.error("评论失败！ comment: {}", JsonUtil.toJson(comment));
        return ServerResponse.createByResponseEnum(ResponseEnum.INSERT_FAILURE);
    }

    @Override
    public ServerResponse<Integer> deleteComment(Long myId, Long commentId) {
        if (myId == null || commentId == null) {
            logger.error("登录者id评论id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        int resultCount = commentDao.countComment(myId, commentId);
        if (resultCount <= 0) {
            logger.warn("没有目标评论！删除失败！");
            return ServerResponse.createBySuccess(0);
        }
        resultCount = commentDao.deleteComment(commentId);
        if (resultCount > 0) {
            logger.info("删除成功！ myId: {}, commentId: {}", myId, commentId);
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("删除失败！ myId: {}, commentId: {}", myId, commentId);
        return ServerResponse.createByResponseEnum(ResponseEnum.DELETE_FAILURE);
    }

    @Override
    public ServerResponse<Integer> good(Long commentId) {
        if (commentId == null) {
            logger.error("评论id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        int resultCount = commentDao.countComment(null, commentId);
        if (resultCount <= 0) {
            logger.error("评论不存在！ commentId: {}", commentId);
            return ServerResponse.createByResponseEnum(ResponseEnum.COMMENT_NOT_FOUND);
        }
        Comment comment = new Comment();
        comment.setId(commentId);
        resultCount = commentDao.good(commentId);
        if (resultCount > 0) {
            logger.info("点赞成功！ commentId: {}", commentId);
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("点赞失败！ commentId: {}", commentId);
        return ServerResponse.createByResponseEnum(ResponseEnum.COMMENT_GOOD_FAILURE);
    }


}
