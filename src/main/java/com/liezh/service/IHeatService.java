package com.liezh.service;

import com.liezh.domain.dto.ServerResponse;

/**
 * Created by Administrator on 2018/4/30.
 */
public interface IHeatService {

    ServerResponse getHeatByMaterialNameAndAmount(String name, Float amount);

}
