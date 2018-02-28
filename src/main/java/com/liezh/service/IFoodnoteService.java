package com.liezh.service;

import com.github.pagehelper.PageInfo;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.foodnote.FoodnoteInfoDto;
import com.liezh.domain.dto.foodnote.FoodnoteQuery;
import com.liezh.domain.entity.Foodnote;
import com.sun.istack.internal.Nullable;

/**
 * Created by Administrator on 2018/2/26.
 */
public interface IFoodnoteService {

    ServerResponse<PageInfo> queryFoodnote(@Nullable Long myId, FoodnoteQuery query, Integer pageNum, Integer pageSize);

    ServerResponse<FoodnoteInfoDto> queryFoodnoteById(@Nullable Long myId, Long foodnoteId);

    ServerResponse<Long> insertFoodnote(Foodnote foodnote);

    ServerResponse<Integer> updateFoodnote(Foodnote foodnote);

    ServerResponse<Integer> deleteFoodnote(Long myId, Long foodnoteId);

    ServerResponse<Integer> releaseFoodnote(Long myId, Long foodnoteId);

    ServerResponse<Integer> good(Long foodnoteId);

}
