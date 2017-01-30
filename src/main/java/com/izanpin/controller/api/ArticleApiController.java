package com.izanpin.controller.api;

import com.github.pagehelper.PageInfo;
import com.izanpin.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by pengyuancong on 2017/1/29.
 */
@Controller
@RequestMapping(value = "/api/article")
public class ArticleApiController {
    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/{page}/{size}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo getArticles(@PathVariable Integer page, @PathVariable Integer size) {
        return articleService.getArticles(page, size);
    }
}