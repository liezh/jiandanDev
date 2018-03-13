package com.liezh.controller;

import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.foodnote.FoodnoteQueryDto;
import com.liezh.domain.entity.Foodnote;
import com.liezh.service.IFoodnoteService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/2/28.
 */
@RestController
@RequestMapping("/api/f")
public class FoodnoteController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(FoodnoteController.class);

    @Autowired
    private IFoodnoteService foodnoteService;

    @GetMapping("/search")
    public ServerResponse queryFoodnote(@RequestParam(value = "query", required = false) String query,
                                         @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        FoodnoteQueryDto foodnoteQueryDto = new FoodnoteQueryDto();
        foodnoteQueryDto.setTitle(query);
        foodnoteQueryDto.setStatus(GlobalConstants.STATUS_RELEASE);
        return foodnoteService.queryFoodnote(null, foodnoteQueryDto, pageNum, pageSize);
    }

    @GetMapping("/{fid}")
    public ServerResponse queryFoodnote(@PathVariable("fid") Long fid) {
        if (fid == null) {
            logger.error("食记id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = this.getAuthUserId();
        return foodnoteService.queryFoodnoteById(myId, fid);
    }

    @PostMapping
    public ServerResponse insertFoodnote(@RequestBody Foodnote foodnote) {
        if (foodnote == null || StringUtils.isBlank(foodnote.getTitle())) {
            logger.error("食记标题为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = this.getAuthUserId();
        foodnote.setAuthorId(myId);
        return foodnoteService.insertFoodnote(foodnote);
    }

    @PutMapping
    public ServerResponse updateFoodnote(@RequestBody Foodnote foodnote) {
        if (foodnote == null || foodnote.getId() == null) {
            logger.error("食记id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = this.getAuthUserId();
        foodnote.setAuthorId(myId);
        return foodnoteService.updateFoodnote(foodnote);
    }

    @DeleteMapping
    public ServerResponse deleteFoodnote(@PathVariable("fid") Long fid) {
        if (fid == null) {
            logger.error("食记id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = this.getAuthUserId();
        return foodnoteService.deleteFoodnote(myId, fid);
    }

    @PostMapping("/release")
    public ServerResponse releaseAndSaveFoodnote(@RequestBody Foodnote foodnote) {

        Long myId = this.getAuthUserId();
        foodnote.setAuthorId(myId);
        foodnote.setStatus(GlobalConstants.STATUS_RELEASE);
        if (foodnote.getId() == null) {
            return foodnoteService.insertFoodnote(foodnote);
        }
        return foodnoteService.updateFoodnote(foodnote);
    }

    @PutMapping("/release/{fid}")
    public ServerResponse releaseFoodnote(@PathVariable("fid") Long fid) {
        if (fid == null) {
            logger.error("食记id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getAuthUserId();
        return foodnoteService.releaseFoodnote(myId, fid);
    }

    @PutMapping("/{fid}/good")
    public ServerResponse good(@PathVariable("fid") Long fid) {
        if (fid == null) {
            logger.error("食记id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getAuthUserId();
        return foodnoteService.good(fid);
    }

    @GetMapping("/{uid}/user")
    public ServerResponse getFoodnoteByUserId(@PathVariable("uid") Long uid,
                                              @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (uid == null) {
            logger.error("用户id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        Long myId = getAuthUserId();
        FoodnoteQueryDto foodnoteQueryDto = new FoodnoteQueryDto();
        if (myId == null || myId != uid ) {
            foodnoteQueryDto.setStatus(GlobalConstants.STATUS_RELEASE);
        }
        foodnoteQueryDto.setAuthorId(uid);
        return foodnoteService.queryFoodnote(myId, foodnoteQueryDto, pageNum, pageSize);

    }

}
