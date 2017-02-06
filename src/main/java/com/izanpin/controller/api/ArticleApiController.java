package com.izanpin.controller.api;

import com.github.pagehelper.PageInfo;
import com.izanpin.data.ImportData;
import com.izanpin.entity.Article;
import com.izanpin.service.ArticleService;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @ApiOperation(value = "获取 无聊图/段子")
    @RequestMapping(value = "/{page}/{size}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo getArticles(@ApiParam(value = "页码") @PathVariable Integer page, @ApiParam(value = "页大小") @PathVariable Integer size) {
        return articleService.getArticles(page, size);
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
    public PageInfo getPictures(@ApiParam(value = "页码") @PathVariable Integer page, @ApiParam(value = "页大小") @PathVariable Integer size) {
        return articleService.getPictures(page, size);
    }

    @ApiOperation(value = "获取段子")
    @RequestMapping(value = "/joke/{page}/{size}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo getJokes(@ApiParam(value = "页码") @PathVariable Integer page, @ApiParam(value = "页大小") @PathVariable Integer size) {
        return articleService.getJokes(page, size);
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

    @ApiIgnore
    @ApiOperation(value = "导入数据")
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public void importData() throws Exception {
        importData.importData();
    }
}