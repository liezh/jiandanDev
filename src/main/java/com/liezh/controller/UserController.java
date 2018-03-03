package com.liezh.controller;

import com.liezh.domain.constant.GlobalConstants;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.domain.dto.foodnote.FoodnoteQueryDto;
import com.liezh.domain.dto.recipe.RecipeQueryDto;
import com.liezh.domain.dto.subject.SubjectQueryDto;
import com.liezh.domain.dto.user.UserInfoDto;
import com.liezh.domain.dto.user.UserQueryDto;
import com.liezh.domain.entity.User;
import com.liezh.service.IFoodnoteService;
import com.liezh.service.IRecipeService;
import com.liezh.service.ISubjectService;
import com.liezh.service.IUserService;
import com.liezh.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2018/3/3.
 */
@RestController
@RequestMapping("/api/u")
public class UserController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private IRecipeService recipeService;

    @Autowired
    private ISubjectService subjectService;

    @Autowired
    private IFoodnoteService foodnoteService;

    @GetMapping("/search")
    public ServerResponse searchUser(@RequestParam(required = false) String query,
                                    @RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize) {

        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        Long myId = getLoginUserId();
        UserQueryDto userQueryDto = new UserQueryDto();
        userQueryDto.setUsername(query);
        return userService.queryUser(myId, userQueryDto, pageNum, pageSize);
    }

    /**
     *  发现用户
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    public ServerResponse getAllUser(@RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize) {
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        Long myId = getLoginUserId();
        return userService.queryUser(myId, null, pageNum, pageSize);
    }

    /**
     *  获取用户详细信息
     * @param uid
     * @return
     */
    @GetMapping("/detail")
    public ServerResponse getUserById() {

        Long myId = getLoginUserId();
        return userService.queryUserById(null, myId);
    }

    @PutMapping
    public ServerResponse update(UserInfoDto userInfoDto) {
        if (userInfoDto == null || userInfoDto.getId() == null) {
            logger.error("用户id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        return userService.updateUser(userInfoDto);
    }

    /**
     *  用户主页
     * @param uid
     * @return
     */
    @GetMapping("/{uid}/main")
    public ServerResponse mainPage(@PathVariable("uid") Long uid) {
        if (uid == null) {
            logger.error("用户id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Map<String, Object> resultMap = new TreeMap<>();
        Integer pageNum = GlobalConstants.PAGE_NUM;
        Integer pageSize = GlobalConstants.PAGE_SIZE;
        Long myId = getLoginUserId();

        // 获取个人信息
        ServerResponse userSR = userService.queryUserById(myId, uid);
        resultMap.put("userInfo", userSR.getData());

        // 获取菜谱页首页
        RecipeQueryDto rq = new RecipeQueryDto();
        rq.setAuthorId(uid);
        rq.setStatus(GlobalConstants.STATUS_RELEASE);
        ServerResponse recipeSR= recipeService.queryRecipe(myId, rq, pageNum, pageSize);
        resultMap.put("recipePage", recipeSR.getData());

        // 主题页首页
        SubjectQueryDto sq = new SubjectQueryDto();
        sq.setCreatorId(uid);
        ServerResponse subjectSR = subjectService.querySubject(myId, sq, pageNum, pageSize);
        resultMap.put("subjectPage", subjectSR.getData());

        // 食记首页
        FoodnoteQueryDto fq = new FoodnoteQueryDto();
        fq.setAuthorId(uid);
        fq.setStatus(GlobalConstants.STATUS_RELEASE);
        ServerResponse foodnoteSR = foodnoteService.queryFoodnote(myId, fq, pageNum, pageSize);
        resultMap.put("foodnotePage", foodnoteSR.getData());

        return ServerResponse.createBySuccess(resultMap);
    }

    /**
     *  获取粉丝列表
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/{uid}/fans")
    public ServerResponse getFans(@PathVariable("uid") Long uid,
                                  @RequestParam(required = false) Integer pageNum,
                                  @RequestParam(required = false) Integer pageSize) {
        if (uid == null) {
            logger.error("用户id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        return userService.getAllFans(uid, pageNum, pageSize);
    }

    /**
     *  获取关注列表
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/{uid}/idols")
    public ServerResponse getIdols(@PathVariable("uid") Long uid,
                                  @RequestParam(required = false) Integer pageNum,
                                  @RequestParam(required = false) Integer pageSize) {
        if (uid == null) {
            logger.error("用户id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        if (pageNum == null || pageSize == null
                || pageNum <= 0 || pageSize <= 0) {
            pageNum = GlobalConstants.PAGE_NUM;
            pageSize = GlobalConstants.PAGE_SIZE;
        }
        return userService.getAllIdols(uid, pageNum, pageSize);
    }

    @PostMapping("/follow/{uid}")
    public ServerResponse follow(@PathVariable("uid") Long uid) {
        if (uid == null) {
            logger.error("关注的用户id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getLoginUserId();
        return userService.follow(myId, uid);
    }

    @PutMapping("/unfollow/{uid}")
    public ServerResponse unfollow(@PathVariable("uid") Long uid) {
        if (uid == null) {
            logger.error("关注的用户id为空！");
            return ServerResponse.createByResponseEnum(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Long myId = getLoginUserId();
        return userService.unfollow(myId, uid);
    }

}
