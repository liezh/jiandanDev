package com.liezh.service;

import com.github.pagehelper.PageInfo;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.subject.SubjectInfoDto;
import com.liezh.domain.dto.subject.SubjectQueryDto;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/**
 * Created by Administrator on 2018/2/20.
 */
public interface ISubjectService {

    ServerResponse<PageInfo> querySubject(Long myId, SubjectQueryDto subjectQueryDto, Integer pageNum, Integer pageSize);

    ServerResponse<SubjectInfoDto> querySubjectById(Long myId, Long subjectId);

    ServerResponse<Long> insertSubject(SubjectInfoDto subjectInfoDto);

    ServerResponse<Integer> updateSubject(SubjectInfoDto subjectInfoDto);

    ServerResponse<Integer> deleteSubject(Long myId, Long subjectId);

    ServerResponse<Integer> contribute(Long myId, Long subjectId, Long recipeId);

    ServerResponse<PageInfo> getPassRecipeBySubjectId(@Nullable Long myId, Long subjectId, Integer pageNum, Integer pageSize);

    ServerResponse<PageInfo> getToCheckRecipeBySubjectId(@NotNull Long myId, Long subjectId, Integer pageNum, Integer pageSize);

    ServerResponse<Integer> pass(Long myId, Long subjectId, Long recipeId);

    ServerResponse<Integer> reject(Long myId, Long subjectId, Long recipeId);

//    ServerResponse<PageInfo> getSubjectByRecipeId(Long recipeId, Integer pageNum, Integer pageSize);

    ServerResponse collect(@NotNull Long myId, Long subjectId);

    ServerResponse uncollect(@NotNull Long myId, Long subjectId);

    ServerResponse<PageInfo> getAllFavorite(@NotNull Long myId, Integer pageNum, Integer pageSize);


}
