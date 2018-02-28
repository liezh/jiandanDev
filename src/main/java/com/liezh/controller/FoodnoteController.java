package com.liezh.controller;

import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.foodnote.FoodnoteQuery;
import com.liezh.domain.entity.Foodnote;
import com.liezh.service.IFoodnoteService;
import com.liezh.service.impl.CommentServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/2/28.
 */
@RestController
@RequestMapping("/api/f/")
public class FoodnoteController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(FoodnoteController.class);

    private IFoodnoteService foodnoteService;

    @GetMapping
    public ServerResponse queryFoodnote(@RequestParam(value = "query") String query,
                                         @RequestParam(value = "pageNum") Integer pageNum,
                                         @RequestParam(value = "pageSize") Integer pageSize) {
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        FoodnoteQuery foodnoteQuery = new FoodnoteQuery();
        foodnoteQuery.setTitle(query);
        return foodnoteService.queryFoodnote(null, foodnoteQuery, pageNum, pageSize);
    }

    public ServerResponse queryFoodnote(@PathVariable("fid") Long fid) {
        if (fid == null) {
            logger.error("食记id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = this.getLoginUserId();
        return foodnoteService.queryFoodnoteById(myId, fid);
    }

    public ServerResponse insertFoodnote(Foodnote foodnote) {
        if (foodnote == null || StringUtils.isBlank(foodnote.getTitle())) {
            logger.error("食记标题为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = this.getLoginUserId();
        foodnote.setAuthorId(myId);
        return foodnoteService.insertFoodnote(foodnote);
    }

    public ServerResponse updateFoodnote(Foodnote foodnote) {
        if (foodnote == null || foodnote.getId() == null) {
            logger.error("食记id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = this.getLoginUserId();
        foodnote.setAuthorId(myId);
        return foodnoteService.updateFoodnote(foodnote);
    }

    public ServerResponse deleteFoodnote(@PathVariable("fid") Long fid) {
        if (fid == null) {
            logger.error("食记id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = this.getLoginUserId();
        return foodnoteService.deleteFoodnote(myId, fid);
    }



}
