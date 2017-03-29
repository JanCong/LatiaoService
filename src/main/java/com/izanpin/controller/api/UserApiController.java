package com.izanpin.controller.api;

import com.izanpin.dto.LoginDto;
import com.izanpin.dto.ResultDto;
import com.izanpin.dto.SmsLoginDto;
import com.izanpin.entity.User;
import com.izanpin.entity.UserToken;
import com.izanpin.enums.ResultStatus;
import com.izanpin.service.UserService;
import com.izanpin.service.UserTokenService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by St on 2017/3/2.
 */
@Api("用户")
@RestController
@RequestMapping("/api/user")
public class UserApiController {
    @Autowired
    UserService userService;
    @Autowired
    UserTokenService userTokenService;

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<UserToken> login(@RequestBody LoginDto dto) {
        ResultDto<UserToken> result;
        try {
            UserToken userToken = userService.login(dto);
            result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), userToken);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }
        return result;
    }

    @ApiOperation("短信验证码登录")
    @RequestMapping(value = "/smsLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<UserToken> smsLogin(@RequestBody SmsLoginDto dto) {
        ResultDto<UserToken> result;
        try {
            UserToken userToken = userService.smsLogin(dto);
            result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), userToken);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }
        return result;
    }

    @ApiOperation("获取用户")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<User> get(@PathVariable Long id) {
        ResultDto<User> result;
        try {
            User user = userService.getUser(id);
            result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), user);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }
        return result;
    }

    @ApiOperation(value = "更新用户昵称")
    @RequestMapping(value = "/{id}/nickname", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto updateNickname(
            @RequestHeader("token") String token,
            @PathVariable Long id,
            @RequestParam String nickname) {
        ResultDto result;
        try {
            UserToken userToken = userTokenService.getUserTokenByToken(token);
            if (userToken != null && userToken.getUserId().equals(id)) {
                userService.updateNickNmae(id, nickname);
                result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), null);
            } else {
                result = new ResultDto(ResultStatus.FAILED.getValue(), "token 错咯", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }
        return result;
    }

    @ApiOperation(value = "更新用户密码")
    @RequestMapping(value = "/{id}/password", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto updatePassword(
            @RequestHeader("token") String token,
            @PathVariable Long id,
            @RequestParam String password) {
        ResultDto result;
        try {
            UserToken userToken = userTokenService.getUserTokenByToken(token);
            if (userToken != null && userToken.getUserId().equals(id)) {
                userService.updatePassword(id, password);
                result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), null);
            } else {
                result = new ResultDto(ResultStatus.FAILED.getValue(), "token 错咯", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }
        return result;
    }

    @ApiOperation(value = "更新用户头像")
    @RequestMapping(value = "/{id}/avatar", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto updateAvatar(
            @RequestHeader("token") String token,
            @PathVariable Long id,
            @RequestParam MultipartFile imageFile) {
        ResultDto result;
        try {
            UserToken userToken = userTokenService.getUserTokenByToken(token);
            if (userToken != null && userToken.getUserId().equals(id)) {
                userService.updateAvatar(id, imageFile);
                result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), null);

            } else {
                result = new ResultDto(ResultStatus.FAILED.getValue(), "token 错咯", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }
        return result;
    }
}
