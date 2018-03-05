package com.liezh.controller;

import com.liezh.domain.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by Administrator on 2018/2/28.
 */
public class BaseController {

    protected static User getLoginUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = new User();
        user.setAccount(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        return user;
    }

    protected static Long getLoginUserId() {
        return 1L;
    }


}
