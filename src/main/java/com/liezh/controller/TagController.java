package com.liezh.controller;

import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.entity.Tag;
import com.liezh.service.ITagService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/3/3.
 */
@RestController
@RequestMapping("/api/t")
public class TagController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private ITagService tagService;

    @PostMapping
    @PreAuthorize("authenticated")
    public ServerResponse InsertTag(@RequestBody Tag tag) {
        if (tag == null || StringUtils.isBlank(tag.getName())) {
            logger.error("标签名为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        return tagService.insertTag(tag);
    }

    @GetMapping("/{tid}")
    public ServerResponse getRecipesByTag(@PathVariable(value = "tid") Long tid,
                                             @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (tid == null) {
            logger.error("标签id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        Tag tag = new Tag();
        tag.setId(tid);
        return tagService.getRecipesByTag(tag, pageNum, pageSize);
    }

    @GetMapping("/search")
    public ServerResponse getRecipesByTag(@RequestParam(value = "query", required = false) String query,
                                          @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (StringUtils.isBlank(query)) {
            logger.error("标签id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        Tag tag = new Tag();
        tag.setName(query);
        return tagService.getRecipesByTag(tag, pageNum, pageSize);
    }


}
