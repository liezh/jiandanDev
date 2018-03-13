package com.liezh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liezh.dao.FoodnoteDao;
import com.liezh.dao.UserDao;
import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.foodnote.FoodnoteInfoDto;
import com.liezh.domain.dto.foodnote.FoodnoteQueryDto;
import com.liezh.domain.entity.Foodnote;
import com.liezh.service.IFoodnoteService;
import com.liezh.utils.JsonUtil;
import com.sun.istack.internal.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 */
@Service
public class FoodnoteServiceImpl implements IFoodnoteService {

    private static Logger logger = LoggerFactory.getLogger(FoodnoteServiceImpl.class);

    @Autowired
    private FoodnoteDao foodnoteDao;

    @Autowired
    private UserDao userDao;

    public ServerResponse<PageInfo> queryFoodnote(@Nullable Long myId, FoodnoteQueryDto query, Integer pageNum, Integer pageSize) {
        if (query == null) {
            query = new FoodnoteQueryDto();
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        Foodnote foodnote = new Foodnote();
        foodnote.setTitle(query.getTitle());
        foodnote.setStatus(query.getStatus());
        foodnote.setAuthorId(query.getAuthorId());
        PageHelper.startPage(pageNum, pageSize);
        List<FoodnoteInfoDto> foodnoteInfoDtos = foodnoteDao.queryFoodnote(foodnote);
        PageInfo pageInfo = new PageInfo(foodnoteInfoDtos);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse<FoodnoteInfoDto> queryFoodnoteById(@Nullable Long myId, Long foodnoteId) {
        if (foodnoteId == null) {
            logger.error("食记id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        FoodnoteInfoDto foodnoteInfoDto = foodnoteDao.queryFoodnoteById(foodnoteId);

        if (foodnoteInfoDto == null || foodnoteInfoDto.getId() == null) {
            logger.error("食记不存在！ myId: {}, recipeId: {}", myId, foodnoteId);
            return ServerResponse.createByResponseEnum(ResponseEnum.RECORD_NOT_FOUND);
        }

        // 判断登录人不是作者, 且文章未发布时
        if (( myId == null || !myId.equals(foodnoteInfoDto.getAuthorId()) ) && foodnoteInfoDto.getStatus() != GlobalConstants.STATUS_RELEASE) {
            logger.error("非作者不可参看草稿！ foodnoteInfo: {}", JsonUtil.toJson(foodnoteInfoDto));
            return ServerResponse.createByResponseEnum(ResponseEnum.PERMISSION_DENIED);
        }

        if (myId != null) {
            // 设置关注标记
            int resultCount = userDao.countIdolById(myId, foodnoteInfoDto.getAuthorId());
            foodnoteInfoDto.setAuthorHasFollow(resultCount > 0);
        } else {
            foodnoteInfoDto.setAuthorHasFollow(false);
        }
        // 增加阅读量
        foodnoteDao.updateFoodnoteReadCount(foodnoteId);

        return ServerResponse.createBySuccess(foodnoteInfoDto);
    }

    public ServerResponse<Long> insertFoodnote(Foodnote foodnote) {
        if (foodnote == null || foodnote.getAuthorId() == null
                || StringUtils.isBlank(foodnote.getTitle())) {
            logger.error("食记作者id或标题为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
//        foodnote.setStatus(foodnote.getStatus());
        int resultCount = foodnoteDao.insertFoodnote(foodnote);
        if (resultCount > 0) {
            logger.info("添加食记成功！ foodnote: {}", JsonUtil.toJson(foodnote));
            return ServerResponse.createBySuccess(foodnote.getId());
        }
        logger.error("添加食记失败！ foodnote: {}", JsonUtil.toJson(foodnote));
        return ServerResponse.createByResponseEnum(ResponseEnum.INSERT_FAILURE);
    }

    public ServerResponse<Integer> updateFoodnote(Foodnote foodnote) {
        if (foodnote == null || foodnote.getId() == null || foodnote.getAuthorId() == null) {
            logger.error("食记id或作者id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        // 判断食记是否存在
        int resultCount = foodnoteDao.countFoodnote(foodnote.getAuthorId(), foodnote.getId());
        if (resultCount <= 0) {
            logger.error("登录用户不拥有该食记！ foodnote: {}", JsonUtil.toJson(foodnote));
            return ServerResponse.createByResponseEnum(ResponseEnum.FOODNOTE_NOT_FOUND);
        }

        resultCount = foodnoteDao.updateFoodnote(foodnote);
        if (resultCount > 0) {
            logger.info("更新成功！ foodnote: {}", JsonUtil.toJson(foodnote));
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("更新失败！ foodnote: {}", JsonUtil.toJson(foodnote));
        return ServerResponse.createByResponseEnum(ResponseEnum.UPDATE_FAILURE);
    }

    public ServerResponse<Integer> deleteFoodnote(Long myId, Long foodnoteId) {
        if (myId == null || foodnoteId == null) {
            logger.error("登录用户id或食记id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        int resultCount = foodnoteDao.countFoodnote(myId, foodnoteId);
        // 判断食记是否存在
        if (resultCount <= 0) {
            logger.error("用户未拥有该食记！ myid: {}, foodnoteId: {}", myId, foodnoteId);
            return ServerResponse.createByResponseEnum(ResponseEnum.FOODNOTE_NOT_FOUND);
        }

        resultCount = foodnoteDao.deleteFoodnote(foodnoteId);
        if (resultCount > 0) {
            logger.info("删除成功！myId: {}, foodnote: {}", myId, foodnoteId);
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("删除失败！ myId: {}, foodnote: {}", myId, foodnoteId);
        return ServerResponse.createByResponseEnum(ResponseEnum.DELETE_FAILURE);
    }

    public ServerResponse<Integer> releaseFoodnote(Long myId, Long foodnoteId) {
        if (myId == null || foodnoteId == null) {
            logger.error("登录人id或食记idweikong！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        int resultCount = foodnoteDao.countFoodnote(myId, foodnoteId);
        // 判断食记是否存在
        if (resultCount <= 0) {
            logger.error("用户未拥有该食记！ myid: {}, foodnoteId: {}", myId, foodnoteId);
            return ServerResponse.createByResponseEnum(ResponseEnum.FOODNOTE_NOT_FOUND);
        }

        Foodnote foodnote = new Foodnote();
        foodnote.setId(foodnoteId);
        foodnote.setStatus(GlobalConstants.STATUS_RELEASE);
        foodnote.setReleaseTime(new Date());
        resultCount = foodnoteDao.updateFoodnote(foodnote);
        if (resultCount > 0) {
            logger.info("发布成功！ myId: {}, foodnoteId: {}", myId, foodnoteId);
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("发布失败！ myId: {}, foodnoteId: {}", myId, foodnoteId);
        return ServerResponse.createByResponseEnum(ResponseEnum.FOODNOTE_RELEASE_FAILURE);
    }

    public ServerResponse<Integer> good(Long foodnoteId) {
        if (foodnoteId == null) {
            logger.error("食记id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        int resultCount = foodnoteDao.countFoodnote(null, foodnoteId);
        // 判断食记是否存在
        if (resultCount <= 0) {
            logger.error("用户未拥有该食记！ foodnoteId: {}", foodnoteId);
            return ServerResponse.createByResponseEnum(ResponseEnum.FOODNOTE_NOT_FOUND);
        }
        resultCount = foodnoteDao.good(foodnoteId);
        if (resultCount > 0) {
            logger.info("点赞成功！ foodnoteId: {}", foodnoteId);
            return ServerResponse.createBySuccess(resultCount);
        }
        logger.error("点赞失败！ foodnoteId: {}", foodnoteId);
        return ServerResponse.createByResponseEnum(ResponseEnum.FOODNOTE_GOOD_FAILURE);
    }


}
