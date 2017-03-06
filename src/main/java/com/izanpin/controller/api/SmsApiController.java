package com.izanpin.controller.api;

import com.izanpin.dto.LoginDto;
import com.izanpin.dto.ResultDto;
import com.izanpin.entity.User;
import com.izanpin.enums.ResultStatus;
import com.izanpin.service.SmsService;
import com.izanpin.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.apache.shiro.web.filter.mgt.DefaultFilter.user;

/**
 * Created by St on 2017/3/2.
 */
@Api("短信")
@RestController
@RequestMapping("/api/sms")
public class SmsApiController {
    @Autowired
    SmsService smsService;

    @ApiOperation(value = "发送登录验证码")
    @RequestMapping(value = "/sendLoginSecurityCode/{number}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto send(@PathVariable String number) {
        ResultDto result;
        try {
            smsService.sendLoginSecurityCode(number);
            result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), null);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }
        return result;
    }
}
