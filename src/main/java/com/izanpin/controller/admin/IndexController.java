package com.izanpin.controller.admin;

import com.izanpin.common.base.BaseController;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by pengyuancong on 2017/1/29.
 */
@RequestMapping(value = "/admin")
@Controller
public class IndexController extends BaseController {
    /**
     * 进入首页
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String root1() {
        return "/admin/index";
    }

    /**
     * 进入首页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "/admin/index";
    }


    /**
     * 进入首页
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/admin/index";
    }

}