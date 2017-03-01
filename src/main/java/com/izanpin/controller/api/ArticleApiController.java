package com.izanpin.controller.api;

import com.github.pagehelper.PageInfo;
import com.izanpin.data.ImportData;
import com.izanpin.dto.AddArticleDto;
import com.izanpin.dto.RequestArticleTimelineDto;
import com.izanpin.entity.Article;
import com.izanpin.service.ArticleService;
import com.wordnik.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by pengyuancong on 2017/1/29.
 */
@Api(value = "无聊图/段子")
@RestController
@RequestMapping(value = "/api/article")
public class ArticleApiController {
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
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addPicture(
            @ApiParam("imageUrls为图片网络地址数组")
            @RequestBody AddArticleDto dto,
            @ApiParam("上传图片数组")
            @RequestParam(required = false) List<MultipartFile> images) throws Exception {
        articleService.addArticle(dto, images);
    }

    @ApiOperation(value = "导入数据")
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public void importData() throws Exception {
        importData.importData();
    }
}