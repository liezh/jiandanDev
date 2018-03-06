package com.liezh.controller;

import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.subject.SubjectInfoDto;
import com.liezh.domain.dto.subject.SubjectQueryDto;
import com.liezh.service.ISubjectService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/4.
 */
@RestController
@RequestMapping("/api/s")
public class SubjectController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(SubjectController.class);

    @Autowired
    private ISubjectService subjectService;

    @GetMapping("/search")
    public ServerResponse searchController(@RequestParam(required = false) String query,
                                           @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                           @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        SubjectQueryDto subjectQueryDto = new SubjectQueryDto();
        subjectQueryDto.setTitle(query);
        Long myId = getAuthUserId();
        return subjectService.querySubject(myId, subjectQueryDto, pageNum, pageSize);
    }

    @GetMapping("/{sid}")
    public ServerResponse getSubjectById(@PathVariable("sid") Long sid) {
        if (sid == null) {
            logger.error("主题菜单id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getAuthUserId();
        return subjectService.querySubjectById(myId, sid);
    }

    @PostMapping
    public ServerResponse insertSubject(@RequestBody SubjectInfoDto subjectInfoDto) {
        if (subjectInfoDto == null || StringUtils.isBlank(subjectInfoDto.getTitle())) {
            logger.error("主题菜单标题为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getAuthUserId();
        subjectInfoDto.setCreatorId(myId);
        return subjectService.insertSubject(subjectInfoDto);
    }

    @PutMapping
    public ServerResponse updateSubject(@RequestBody SubjectInfoDto subjectInfoDto) {
        if (subjectInfoDto == null || subjectInfoDto.getId() == null) {
            logger.error("主题菜单id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getAuthUserId();
        subjectInfoDto.setCreatorId(myId);
        return subjectService.updateSubject(subjectInfoDto);
    }

    @DeleteMapping("/{sid}")
    public ServerResponse deleteSubject(@PathVariable("sid") Long sid) {
        if (sid == null) {
            logger.error("主题菜单id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getAuthUserId();
        return subjectService.deleteSubject(myId, sid);
    }

    /**
     *  投稿
     * @param params { subjectId: 1, recipeId:２}
     * @return
     */
    @PostMapping("/contribute")
    public ServerResponse contribute(@RequestBody Map<String, Long> params) {
        if (params == null || params.size() < 2) {
            logger.error("主题菜单投稿参数错误！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long subjectId = params.get("subjectId");
        Long recipeId = params.get("recipeId");
        Long myId = getAuthUserId();
        return subjectService.contribute(myId, subjectId, recipeId);
    }


    /**
     *  获取通过了，发布在主题中的菜谱
     * @param sid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/{sid}/pass")
    public ServerResponse getPassRecipeBySubjectId(@PathVariable("sid") Long sid,
                                                   @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (sid == null) {
            logger.error("主题菜单id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        Long myId = getAuthUserId();
        return subjectService.getPassRecipeBySubjectId(myId, sid, pageNum, pageSize);
    }

    /**
     *  获取待审批的投稿菜谱， 投稿箱
     * @param sid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/{sid}/check")
    public ServerResponse getToCheckRecipeBySubjectId(@PathVariable("sid") Long sid,
                                                      @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (sid == null) {
            logger.error("主题菜单id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        Long myId = getAuthUserId();
        return subjectService.getToCheckRecipeBySubjectId(myId, sid, pageNum, pageSize);
    }

    /**
     *  通过该主题下的投稿菜谱
     * @param params  { subjectId: 1, recipeId:２}
     * @return
     */
    @PostMapping("/pass")
    public ServerResponse pass(@RequestBody Map<String, Long> params) {
        if (params == null || params.size() < 2) {
            logger.error("通过主题菜单参数错误！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long subjectId = params.get("subjectId");
        Long recipeId = params.get("recipeId");
        Long myId = getAuthUserId();
        return subjectService.pass(myId, subjectId, recipeId);
    }

    /**
     *  拒绝该主题下的投稿菜谱
     * @param params  { subjectId: 1, recipeId:２}
     * @return
     */
    @PutMapping("/reject")
    public ServerResponse reject(@RequestBody Map<String, Long> params) {
        if (params == null || params.size() < 2) {
            logger.error("拒绝主题菜单参数错误！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long subjectId = params.get("subjectId");
        Long recipeId = params.get("recipeId");
        Long myId = getAuthUserId();
        return subjectService.reject(myId, subjectId, recipeId);
    }

    @GetMapping("/favorite")
    public ServerResponse getAllFavorite(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        Long myId = getAuthUserId();
        return subjectService.getAllFavorite(myId, pageNum, pageSize);
    }

    @PostMapping("/collect/{sid}")
    public ServerResponse collect(@PathVariable("sid") Long sid) {
        if (sid == null) {
            logger.error("收藏菜单参数错误！主题菜单id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getAuthUserId();
        return subjectService.collect(myId, sid);
    }

    @DeleteMapping("/uncollect/{sid}")
    public ServerResponse uncollect(@PathVariable("sid") Long sid) {
        if (sid == null) {
            logger.error("收藏菜单参数错误！主题菜单id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getAuthUserId();
        return subjectService.uncollect(myId, sid);
    }








}
