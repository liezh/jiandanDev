package com.liezh.controller;

import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.service.IHeatService;
import com.mchange.lang.FloatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/4/30.
 */
@RestController
@RequestMapping("/api/h")
public class HeatController {

    private static Logger logger = LoggerFactory.getLogger(HeatController.class);

    @Autowired
    private IHeatService heatService;

    @GetMapping
    public ServerResponse getHeatByMaterialNameAndAmount(@RequestParam("name") String name,
                                                         @RequestParam("amount") Float amount) {
        if (StringUtils.isBlank(name) || amount == null || amount <= 0f) {
            logger.error("食物热量参数错误！ name: {}, amount: {}", name, amount);
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        return heatService.getHeatByMaterialNameAndAmount(name, amount);
    }


}
