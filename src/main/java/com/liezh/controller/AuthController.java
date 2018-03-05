package com.liezh.controller;

import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.user.UserInfoDto;
import com.liezh.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/3/6.
 */
public class AuthController extends BaseController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private IUserService userService;


    @RequestMapping(value = "${jwt.route.authentication.login}", method = RequestMethod.POST)
    public ServerResponse login(@RequestBody UserInfoDto user) throws AuthenticationException {

        return userService.login(user.getUsername(), user.getPassword());
    }

//    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
//    public ServerResponse refresh(
//            HttpServletRequest request) throws AuthenticationException {
//        String token = request.getHeader(tokenHeader);
//        String refreshedToken = userService.refresh(token);
//        if(refreshedToken == null) {
//            return ResponseEntity.badRequest().body(null);
//        } else {
//            return ResponseEntity.ok(new JwtAuthenticationResponse(null, refreshedToken));
//        }
//    }

    @RequestMapping(value = "${jwt.route.authentication.register}", method = RequestMethod.POST)
    public ServerResponse register(@RequestBody UserInfoDto user) throws AuthenticationException {
        return userService.register(user);
    }

//    @GetMapping(value = "/checkValid")
//    public ServerResponse checkValid(String str, String type) {
//        return userService.checkValid(str, type);
//    }

    @GetMapping(value = "/forgetGetQuestion")
    public ServerResponse forgetGetQuestion(String account) {
//        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User)auth.getPrincipal();
        return userService.findQuestionByAccount(account);
    }

    @PostMapping(value = "/forgetCheckAnswer")
    public ServerResponse forgetCheckAnswer(String username, String question, String answer) {
//        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User)auth.getPrincipal();
        return userService.checkAnswer(username, question, answer);
    }

    @PostMapping(value = "/forgetResetPassword")
    public ServerResponse forgetResetPassword(String username, String passwordNew, String forgetToken) {
//        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User)auth.getPrincipal();
        return userService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    @GetMapping(value = "/loginPage")
    public String loginPage() {
        return "login";
    }



}
