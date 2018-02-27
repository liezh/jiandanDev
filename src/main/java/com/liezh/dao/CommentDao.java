package com.liezh.dao;

import com.liezh.domain.dto.comment.CommentInfoDto;
import com.liezh.domain.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/2/15.
 */
@Repository
public interface CommentDao {

    List<CommentInfoDto> queryComment(Comment comment);

    CommentInfoDto queryCommentById(Long commentId);

    Integer insertComment(Comment comment);

    Integer updateComment(Comment comment);

    Integer deleteComment(Long commentId);

    Integer countComment(@Param("publisherId") Long publisherId, @Param("commentId") Long commentId);

    Integer good(Long commentId);

    Integer countTarget(@Param("targetId") Long targetId, @Param("targetType") String targetType);
}
