package com.liezh.controller;

import com.liezh.domain.entity.User;
import com.liezh.security.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Administrator on 2018/2/28.
 */
public class BaseController {

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected static User getAuthUser() {
        if (!isAuthenticated()) {
            logger.warn("=====   没有登录    ====");
            return null;
        }
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = new User();
        user.setId(authUser.getId());
        user.setAccount(authUser.getUsername());
        user.setPassword(authUser.getPassword());
        user.setSalt(authUser.getSalt());
        user.setMobile(authUser.getMobile());
        user.setEmail(authUser.getEmail());
        user.setCreateTime(authUser.getUpdateTime());
        user.setUpdateTime(authUser.getUpdateTime());
        return user;
    }

    protected static Long getAuthUserId() {
        if (!isAuthenticated()) {
            logger.warn("=====   没有登录    ====");
            return null;
        }

        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return authUser.getId();
    }

    protected static String getAuthUserAccount() {
        if (!isAuthenticated()) {
            logger.warn("=====   没有登录    ====");
            return null;
        }
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return authUser.getAccount();
    }

    private static boolean isAuthenticated() {
        return !(SecurityContextHolder.getContext().getAuthentication().getName().
                equals("anonymousUser"));
    }

}
