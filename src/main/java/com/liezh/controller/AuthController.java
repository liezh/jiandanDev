package com.liezh.controller;

import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.user.UserInfoDto;
import com.liezh.service.IUserService;
import com.liezh.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/3/6.
 */
@Controller
public class AuthController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private IUserService userService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse login(@RequestBody UserInfoDto user) throws AuthenticationException {
        if (user == null) {
            logger.error("用户登录信息为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        return userService.login(user.getAccount(), user.getPassword());
    }

    @GetMapping(value = "refresh")
    @ResponseBody
    public ServerResponse refresh(
            HttpServletRequest request) throws AuthenticationException {
        String token = request.getHeader(tokenHeader);
        ServerResponse refreshedServerResponse = userService.refresh(token);
        if(refreshedServerResponse.isSuccess()) {
            logger.info("AuthToken 更新成功！ refreshed: {}", JsonUtil.toJson(refreshedServerResponse));
            return refreshedServerResponse;
        }
        logger.error("AuthToken 更新失败！ refreshed: {}", JsonUtil.toJson(refreshedServerResponse));
        return refreshedServerResponse;
    }

    @PostMapping(value = "/register")
    @ResponseBody
    public ServerResponse register(@RequestBody UserInfoDto user) throws AuthenticationException {
        if (user == null || StringUtils.isBlank(user.getAccount()) || StringUtils.isBlank(user.getPassword())) {
            logger.error("注册用户帐号名或密码为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        return userService.register(user);
    }

//    @GetMapping(value = "/checkValid")
//    public ServerResponse checkValid(String str, String type) {
//        return userService.checkValid(str, type);
//    }

    @GetMapping(value = "/forgetGetQuestion")
    @ResponseBody
    public ServerResponse forgetGetQuestion(@RequestParam(value = "account") String account) {
        return userService.findQuestionByAccount(account);
    }

    @PostMapping(value = "/forgetCheckAnswer")
    @ResponseBody
    public ServerResponse forgetCheckAnswer(String account, String question, String answer) {
        return userService.checkAnswer(account, question, answer);
    }

    @PostMapping(value = "/forgetResetPassword")
    @ResponseBody
    public ServerResponse forgetResetPassword(String account, String passwordNew, String forgetToken) {
        return userService.forgetResetPassword(account, passwordNew, forgetToken);
    }





}
