package com.izanpin.controller.api;

import com.github.pagehelper.PageInfo;
import com.izanpin.data.ImportData;
import com.izanpin.service.ArticleService;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by pengyuancong on 2017/1/29.
 */
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

    @ApiOperation(value = "导入数据")
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public void importData() throws Exception {
        importData.importData();
    }
}