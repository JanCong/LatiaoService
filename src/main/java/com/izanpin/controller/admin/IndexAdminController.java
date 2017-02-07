package com.izanpin.controller.admin;

import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by pengyuancong on 2017/1/29.
 */
@Controller
public class IndexAdminController {

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String index() {
        return "/admin/index";
    }
}