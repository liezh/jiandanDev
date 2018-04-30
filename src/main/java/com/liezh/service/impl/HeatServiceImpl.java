package com.liezh.service.impl;

import com.liezh.dao.HeatDao;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.entity.Heat;
import com.liezh.service.IHeatService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/30.
 */
@Service
public class HeatServiceImpl implements IHeatService {

    private static Logger logger = LoggerFactory.getLogger(HeatServiceImpl.class);

    @Autowired
    private HeatDao heatDao;

    @Override
    public ServerResponse getHeatByMaterialNameAndAmount(String name, Float amount) {
        if (StringUtils.isBlank(name) || amount == null || amount <= 0f) {
            logger.error("食物热量参数错误！ name: {}, amount: {}", name, amount);
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        List<Heat> heatList = heatDao.queryHeat(name);
        Float sumEnergy = 0f;
        if (heatList.size() > 0) {
            sumEnergy = heatList.get(0).getEnergy() * amount;
        }
        Map<String, Float> map = new HashMap<>();
        map.put("energy", rount2(sumEnergy));
        return ServerResponse.createBySuccess(map);
    }

    private Float rount2(Float energy) {
        if (energy == null || energy <= 0) {
            return 0f;
        }
        return Math.round(energy * 100) * 0.01f;
    }


}
