package com.liezh.controller;

import com.liezh.domain.dto.ServerResponse;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/indexPage")
    public String indexFullPage() {
        return "index";
    }

    @GetMapping("/recipePage")
    public String recipePage() {
        return "recipe";
    }

    @GetMapping("/recipeEditPage")
    public String recipeEditPage() {
        return "recipe_edit";
    }

    @GetMapping("/foodnotePage")
    public String foodnotePage() {
        return "foodnote";
    }

    @GetMapping("/foodnoteEditPage")
    public String foodnoteEditPage() {
        return "foodnote_edit";
    }

    @GetMapping("/subjectPage")
    public String subjectPage() {
        return "subject";
    }

    @GetMapping("/searchPage")
    public String searchPage() {
        return "search";
    }

}
