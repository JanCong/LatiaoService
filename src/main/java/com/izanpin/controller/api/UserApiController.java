package com.izanpin.controller.api;

import com.izanpin.dto.LoginDto;
import com.izanpin.dto.ResultDto;
import com.izanpin.dto.SmsLoginDto;
import com.izanpin.entity.User;
import com.izanpin.enums.ResultStatus;
import com.izanpin.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;

/**
 * Created by St on 2017/3/2.
 */
@Api("用户")
@RestController
@RequestMapping("/api/user")
public class UserApiController {
    @Autowired
    UserService userService;

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<User> login(@RequestBody LoginDto dto) {
        ResultDto<User> result;
        try {
            User user = userService.login(dto);
            result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), user);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }
        return result;
    }

    @ApiOperation("短信验证码登录")
    @RequestMapping(value = "/smsLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<User> smsLogin(@RequestBody SmsLoginDto dto) {
        ResultDto<User> result;
        try {
            User user = userService.smsLogin(dto);
            result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), user);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }
        return result;
    }
}
