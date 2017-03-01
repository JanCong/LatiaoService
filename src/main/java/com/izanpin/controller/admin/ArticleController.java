package com.izanpin.controller.admin;

import com.izanpin.common.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by pengyuancong on 2017/1/29.
 */
@RequestMapping(value = "/admin/article")
@Controller
public class ArticleController extends BaseController {

    /**
     * 无聊图
     */
    @RequestMapping(value = "/pictureList", method = RequestMethod.GET)
    public String picture() {
        return "/admin/article/pictureList";
    }

    /**
     * 段子
     */
    @RequestMapping(value = "/jokeList", method = RequestMethod.GET)
    public String joke() {
        return "/admin/article/jokeList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "/admin/article/add";
    }
}