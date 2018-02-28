package com.liezh.controller;

import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.entity.User;

/**
 * Created by Administrator on 2018/2/28.
 */
public class BaseController {

    public User getLoginUser() {
        return null;
    }

    public Long getLoginUserId() {
        return 1L;
    }


}
