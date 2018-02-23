package com.liezh.domain.dto.recipe;

import com.liezh.domain.dto.user.UserInfoDto;

import java.util.Date;

/**
 * Created by Administrator on 2018/2/16.
 */
public class RecipeDetailInfoDto {

    private Long id;

    private String title;

    private String cover;

    private String content;

    private String synopsis;

    private String process;

    private String materials;

    private Integer status;

    private Integer goodCount;

    private Integer readCount;

    private Date releaseTime;

    // 作者信息
    private UserInfoDto authorInfo;

}
