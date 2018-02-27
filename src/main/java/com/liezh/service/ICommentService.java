package com.liezh.service;

import com.github.pagehelper.PageInfo;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.comment.CommentQuery;
import com.liezh.domain.entity.Comment;

/**
 * Created by Administrator on 2018/2/26.
 */
public interface ICommentService {

    ServerResponse<PageInfo> queryComment(CommentQuery query, Integer pageNum, Integer pageSize);

    ServerResponse<Long> insertComment(Comment comment);

    ServerResponse<Integer> deleteComment(Long myId, Long commentId);

    ServerResponse<Integer> good(Long commentId);

}
