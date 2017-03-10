package com.izanpin.controller.api;

import com.github.pagehelper.PageInfo;
import com.izanpin.data.ImportData;
import com.izanpin.dto.AddArticleDto;
import com.izanpin.dto.RequestArticleTimelineDto;
import com.izanpin.entity.Article;
import com.izanpin.service.ArticleService;
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
    private static final Logger logger = LogManager.getLogger(ArticleApiController.class.getName());

    @Autowired
    ArticleService articleService;
    @Autowired
    ImportData importData;

    @ApiOperation(value = "获取 辣条")
    @RequestMapping(value = "/{page}/{size}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo getArticles(@ApiParam(value = "页码") @PathVariable Integer page,
                                @ApiParam(value = "页大小") @PathVariable Integer size,
                                @ApiParam(value = "关键词") @RequestParam(required = false) String keyword) {
        return articleService.getArticles(page, size, keyword);
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
                                @ApiParam(value = "关键词") @RequestParam(required = false) String keyword) {
        return articleService.getPictures(page, size, keyword);
    }

    @ApiOperation(value = "获取段子")
    @RequestMapping(value = "/joke/{page}/{size}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo getJokes(@ApiParam(value = "页码") @PathVariable Integer page,
                             @ApiParam(value = "页大小") @PathVariable Integer size,
                             @ApiParam(value = "关键词") @RequestParam(required = false) String keyword) {
        return articleService.getJokes(page, size, keyword);
    }

    @ApiOperation(value = "赞")
    @RequestMapping(value = "/like/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void like(@PathVariable Long id, @RequestBody(required = false) Long userId) {
        articleService.like(id, userId);
    }

    @ApiOperation(value = "踩")
    @RequestMapping(value = "/hate/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void hate(@PathVariable Long id, @RequestBody(required = false) Long userId) {
        articleService.hate(id, userId);
    }

    @ApiOperation(value = "新增辣条", notes = "imageUrls为图片网络地址数组，images为上传图片数组，可以同时使用")
    @RequestMapping(method = RequestMethod.POST)
    public void add(
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
        AddArticleDto dto = new AddArticleDto(userId, content, device, imageUrls, images);
        articleService.addArticle(dto);
    }


    @ApiOperation(value = "导入数据")
    @RequestMapping(value = "/importData", method = RequestMethod.POST)
    @ResponseBody
    public void importData() throws Exception {
        importData.importData();
    }

    @ApiOperation(value = "测试日志")
    @RequestMapping(value = "/logtest", method = RequestMethod.POST)
    @ResponseBody
    public void logtest() throws Exception {
        logger.trace("trace");
        logger.error("error");
        logger.debug("debug");
        logger.info("info");
        logger.fatal("fatal");
    }
}