package com.izanpin.controller.share;

import com.izanpin.common.base.BaseController;
import com.izanpin.service.ArticleService;
import com.izanpin.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pengyuancong on 2017/1/29.
 */
@RequestMapping(value = "/share")
@Controller
public class ShareController extends BaseController {
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;

    /**
     * 分享
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable("id") Long id, @RequestHeader("user-agent") String userAgent) {
        String appUrl = "https://itunes.apple.com/cn/app/id1223259711";

        if (userAgent != null && userAgent.toLowerCase().contains("android")) {
            appUrl = "http://izanpin.com/app/zanpin.apk";
        }

        ModelAndView mv = new ModelAndView("/share/detail");
        mv.addObject("article", articleService.getById(id, null));
        mv.addObject("comments", commentService.getComments(id, 1, 10));
        mv.addObject("appUrl", "http://appurl.me/15251626");

        return mv;
    }

    /**
     * 分享 自定义
     */
    @RequestMapping(value = "/custom", method = RequestMethod.GET)
    public ModelAndView custom(@RequestParam("url") String url,
                               @RequestHeader("user-agent") String userAgent)
            throws MalformedURLException {
        String appUrl = "https://itunes.apple.com/cn/app/id1223259711";

        if (userAgent != null && userAgent.toLowerCase().contains("android")) {
            appUrl = "http://izanpin.com/app/zanpin.apk";
        }

        ModelAndView mv = new ModelAndView("/share/custom");
        mv.addObject("url", new URL(url));
        mv.addObject("appUrl", "http://appurl.me/15251626");
        return mv;
    }
}