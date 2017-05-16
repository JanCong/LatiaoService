package com.izanpin.controller.api;

import com.izanpin.dto.ResultDto;
import com.izanpin.enums.ResultStatus;
import com.izanpin.service.SmsService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by St on 2017/3/2.
 */
@Api("测试")
@RestController
@RequestMapping("/api/test")
public class TestApiController {
    static Logger logger = LogManager.getLogger();

    @RequestMapping(value = "/logTest", method = RequestMethod.POST)
    @ResponseBody
    public void logTest() {
        logger.error("Did it again!");   //error级别的信息，参数就是你输出的信息
        logger.info("我是info信息");    //info级别的信息
        logger.debug("我是debug信息");
        logger.warn("我是warn信息");
        logger.fatal("我是fatal信息");

        Exception e = new Exception("错误错误错误");
        logger.error("", e);
    }
}
