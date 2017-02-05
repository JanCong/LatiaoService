package com.izanpin.controller.api;

import com.github.pagehelper.PageInfo;
import com.izanpin.data.ImportData;
import com.izanpin.entity.Article;
import com.izanpin.service.ArticleService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by pengyuancong on 2017/1/29.
 */
@Api(value = "Article")
@RestController
@RequestMapping(value = "/api/article")
public class ArticleApiController {
    @Autowired
    ArticleService articleService;
    @Autowired
    ImportData importData;


    @ApiOperation(value = "获取Articles")
    @RequestMapping(value = "/{page}/{size}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo getArticles(@PathVariable Integer page, @PathVariable Integer size) {
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
    public PageInfo getPictures(@PathVariable Integer page, @PathVariable Integer size) {
        return articleService.getPictures(page, size);
    }

    @ApiOperation(value = "获取段子")
    @RequestMapping(value = "/joke/{page}/{size}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo getJokes(@PathVariable Integer page, @PathVariable Integer size) {
        return articleService.getJokes(page, size);
    }

    @ApiOperation(value = "导入数据")
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public void importData() throws Exception {
        importData.importData();
    }
}