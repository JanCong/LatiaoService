package com.izanpin.controller.api;

import com.github.pagehelper.PageInfo;
import com.izanpin.data.ImportData;
import com.izanpin.service.ArticleService;
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


    @RequestMapping(value = "/{page}/{size}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo getArticles(@PathVariable Integer page, @PathVariable Integer size) {
        return articleService.getArticles(page, size);
    }

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    @ResponseBody
    public void importData() throws Exception {
        importData.importData();
    }
}