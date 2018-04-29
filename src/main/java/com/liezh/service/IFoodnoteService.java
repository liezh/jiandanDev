package com.liezh.service;

import com.github.pagehelper.PageInfo;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.foodnote.FoodnoteInfoDto;
import com.liezh.domain.dto.foodnote.FoodnoteQueryDto;
import com.liezh.domain.entity.Foodnote;

/**
 * Created by Administrator on 2018/2/26.
 */
public interface IFoodnoteService {

    ServerResponse<PageInfo> queryFoodnote(Long myId, FoodnoteQueryDto query, Integer pageNum, Integer pageSize);

    ServerResponse<FoodnoteInfoDto> queryFoodnoteById(Long myId, Long foodnoteId);

    ServerResponse<Long> insertFoodnote(Foodnote foodnote);

    ServerResponse<Integer> updateFoodnote(Foodnote foodnote);

    ServerResponse<Integer> deleteFoodnote(Long myId, Long foodnoteId);

    ServerResponse<Integer> releaseFoodnote(Long myId, Long foodnoteId);

    ServerResponse<Integer> good(Long foodnoteId);

}
