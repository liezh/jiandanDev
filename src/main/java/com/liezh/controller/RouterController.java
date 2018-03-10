package com.liezh.controller;

import com.liezh.domain.dto.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Administrator on 2018/3/10.
 */
@Controller
public class RouterController {

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @GetMapping("indexPage")
    public String indexPage() {
        return "index";
    }


}
