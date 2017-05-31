package com.izanpin.controller.api;

import com.github.pagehelper.PageInfo;
import com.izanpin.data.ImportData;
import com.izanpin.dto.AddArticleDto;
import com.izanpin.dto.RequestArticleTimelineDto;
import com.izanpin.dto.RequestFriendArticleTimelineDto;
import com.izanpin.dto.ResultDto;
import com.izanpin.entity.Article;
import com.izanpin.entity.UserToken;
import com.izanpin.enums.ResultStatus;
import com.izanpin.service.ArticleService;
import com.izanpin.service.UserTokenService;
import com.wordnik.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by pengyuancong on 2017/1/29.
 */
@Api(value = "辣条")
@RestController
@RequestMapping(value = "/api/article")
public class ArticleApiController {
    @Autowired
    ArticleService articleService;
    @Autowired
    UserTokenService userTokenService;
    @Autowired
    ImportData importData;

    static Logger logger = LogManager.getLogger();

    @ApiOperation(value = "获取 辣条")
    @RequestMapping(value = "/{page}/{size}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo getArticles(@ApiParam(value = "页码") @PathVariable Integer page,
                                @ApiParam(value = "页大小") @PathVariable Integer size,
                                @ApiParam(value = "关键词") @RequestParam(required = false) String keyword,
                                @ApiParam("当前用户id") @RequestParam(required = false) Long userId) {
        return articleService.getArticles(page, size, keyword, userId);
    }

    @ApiOperation(value = "获取 辣条")
    @RequestMapping(value = "/timeline/{page}/{size}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo getArticlesByTimeline(@ApiParam("页码") @PathVariable Integer page,
                                          @ApiParam("页大小") @PathVariable Integer size,
                                          @ModelAttribute RequestArticleTimelineDto dto) {
        return articleService.getArticlesByTimeline(page, size, dto);
    }

    @ApiOperation(value = "获取最新 辣条 （最多1000条）")
    @RequestMapping(value = "/timeline", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo getArticlesByTimeline(@ModelAttribute RequestArticleTimelineDto dto) {
        return articleService.getArticlesByTimeline(1, 1000, dto);
    }

    @ApiOperation(value = "获取一周内随机 辣条")
    @RequestMapping(value = "/random/week", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto getArticlesByRandomInWeek(@ApiParam("取几条") @RequestParam(required = false) Integer size,
                                               @ApiParam("当前用户id") @RequestParam(required = false) Long userId) {
        return new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), articleService.getArticlesByRandomInWeek(size, userId));
    }

    @ApiOperation(value = "获取推荐辣条")
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto getRecommendArticles(@ApiParam("取几条") @RequestParam(required = false) Integer size,
                                          @ApiParam("当前用户id") @RequestParam(required = false) Long userId) {
        return new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), articleService.getRecommendArticles(size, userId));
    }

    @ApiOperation(value = "获取好友辣条")
    @RequestMapping(value = "/friend/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<Article>> getArticlesByTimeline(
            @RequestHeader("token") String token,
            @ApiParam("根据userId返回该用户的好友的辣条") @PathVariable Long userId,
            @ModelAttribute RequestFriendArticleTimelineDto dto) {
        ResultDto result;
        try {
            UserToken userToken = userTokenService.getUserTokenByToken(token);
            if (userToken != null && userToken.getUserId().equals(userId)) {
                result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), articleService.getFriendArticlesByUserId(1, 1000, userId, dto));
            } else {
                result = new ResultDto(ResultStatus.FAILED.getValue(), "token 错咯", null);
            }
        } catch (Exception e) {
            logger.error("", e);
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }

        return result;
    }

    @ApiOperation(value = "根据ID获取")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Article get(@PathVariable Long id) {
        return articleService.getById(id);
    }

    @ApiOperation(value = "获取无聊图")
    @RequestMapping(value = "/picture/{page}/{size}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo getPictures(@ApiParam(value = "页码") @PathVariable Integer page,
                                @ApiParam(value = "页大小") @PathVariable Integer size,
                                @ApiParam(value = "关键词") @RequestParam(required = false) String keyword,
                                @ApiParam("当前用户id") @RequestParam(required = false) Long userId) {
        return articleService.getPictures(page, size, keyword, userId);
    }

    @ApiOperation(value = "获取段子")
    @RequestMapping(value = "/joke/{page}/{size}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo getJokes(@ApiParam(value = "页码") @PathVariable Integer page,
                             @ApiParam(value = "页大小") @PathVariable Integer size,
                             @ApiParam(value = "关键词") @RequestParam(required = false) String keyword,
                             @ApiParam("当前用户id") @RequestParam(required = false) Long userId) {
        return articleService.getJokes(page, size, keyword, userId);
    }

    @ApiOperation(value = "赞")
    @RequestMapping(value = "/like/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto like(
            @RequestHeader("token") String token,
            @PathVariable Long id,
            @RequestParam Long userId) {
        ResultDto result;
        try {
            UserToken userToken = userTokenService.getUserTokenByToken(token);
            if (userToken != null && userToken.getUserId().equals(userId)) {
                articleService.like(id, userId);
                result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), null);
            } else {
                result = new ResultDto(ResultStatus.FAILED.getValue(), "token 错咯", null);
            }
        } catch (Exception e) {
            logger.error("", e);
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }
        return result;
    }

    @ApiOperation(value = "踩")
    @RequestMapping(value = "/hate/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto hate(
            @RequestHeader("token") String token,
            @PathVariable Long id,
            @RequestParam Long userId) {
        ResultDto result;
        try {
            UserToken userToken = userTokenService.getUserTokenByToken(token);
            if (userToken != null && userToken.getUserId().equals(userId)) {
                articleService.hate(id, userId);
                result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), null);
            } else {
                result = new ResultDto(ResultStatus.FAILED.getValue(), "token 错咯", null);
            }
        } catch (Exception e) {
            logger.error("", e);
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }
        return result;
    }

    @ApiOperation(value = "新增辣条", notes = "imageUrls为图片网络地址数组，images为上传图片数组，可以同时使用")
    @RequestMapping(method = RequestMethod.POST)
    public ResultDto add(
            @RequestHeader("token") String token,
            @ApiParam("用户ID")
            @RequestParam Long userId,
            @ApiParam("内容")
            @RequestParam(required = false) String content,
            @ApiParam("设备")
            @RequestParam(required = false) String device,
            @ApiParam("图片网络地址数组")
            @RequestParam(required = false) List<String> imageUrls,
            @ApiParam("上传图片数组")
            @RequestParam(required = false) List<MultipartFile> images) throws Exception {
        ResultDto result;
        try {
            UserToken userToken = userTokenService.getUserTokenByToken(token);
            if (userToken != null && userToken.getUserId().equals(userId)) {
                AddArticleDto dto = new AddArticleDto(userId, content, device, imageUrls, images);
                articleService.addArticle(dto);
                result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), null);
            } else {
                result = new ResultDto(ResultStatus.FAILED.getValue(), "token 错咯", null);
            }
        } catch (Exception e) {
            logger.error("", e);
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }
        return result;
    }


    @ApiOperation(value = "导入数据")
    @RequestMapping(value = "/importData", method = RequestMethod.POST)
    @ResponseBody
    public void importData() throws Exception {
        importData.importData();
    }

    @ApiOperation(value = "导入图片数据")
    @RequestMapping(value = "/importPictureData", method = RequestMethod.POST)
    @ResponseBody
    public void importPictureData() throws Exception {
        importData.importPictures();
    }
}