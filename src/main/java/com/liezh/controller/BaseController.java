package com.liezh.controller;

import com.liezh.domain.entity.User;

/**
 * Created by Administrator on 2018/2/28.
 */
public class BaseController {

    protected static User getLoginUser() {
        return null;
    }

    protected static Long getLoginUserId() {
        return 1L;
    }


}
