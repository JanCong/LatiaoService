package com.izanpin.controller.api;

import com.izanpin.dto.LoginDto;
import com.izanpin.dto.OAuthLoginDto;
import com.izanpin.dto.ResultDto;
import com.izanpin.dto.SmsLoginDto;
import com.izanpin.entity.User;
import com.izanpin.entity.UserToken;
import com.izanpin.enums.ResultStatus;
import com.izanpin.service.UserService;
import com.izanpin.service.UserTokenService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.StringJoiner;

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

    static Logger logger = LogManager.getLogger();

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto login(@RequestBody LoginDto dto) {
        ResultDto result;
        try {
            UserToken userToken = userService.login(dto);
            User user = userService.getUser(userToken.getUserId());

            HashMap map = new HashMap();
            map.put("token", userToken);
            map.put("user", user);
            result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), map);
        } catch (Exception e) {
            logger.error("", e);
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }
        return result;
    }

    @ApiOperation("短信验证码登录")
    @RequestMapping(value = "/smsLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto smsLogin(@RequestBody SmsLoginDto dto) {
        ResultDto result;
        try {
            UserToken userToken = userService.smsLogin(dto);
            User user = userService.getUser(userToken.getUserId());

            HashMap map = new HashMap();
            map.put("token", userToken);
            map.put("user", user);
            result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), map);
        } catch (Exception e) {
            logger.error("", e);
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }
        return result;
    }

    @ApiOperation("第三方OAuth登录")
    @RequestMapping(value = "/oauthLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto oauthLogin(@RequestBody OAuthLoginDto dto) {
        ResultDto result;
        try {
            UserToken userToken = userService.oauthLogin(dto);
            User user = userService.getUser(userToken.getUserId());

            HashMap map = new HashMap();
            map.put("token", userToken);
            map.put("user", user);
            result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), map);
        } catch (Exception e) {
            logger.error("", e);
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
            logger.error("", e);
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
            logger.error("", e);
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
            logger.error("", e);
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
            logger.error("", e);
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }
        return result;
    }

    @ApiOperation("添加好友")
    @RequestMapping(value = "/{id}/friend", method = RequestMethod.POST)
    public ResultDto addFriend(
            @RequestHeader("token") String token,
            @PathVariable Long id,
            @RequestParam Long friendId,
            @RequestParam(required = false) String remark
    ) {
        ResultDto result;
        try {
            UserToken userToken = userTokenService.getUserTokenByToken(token);
            if (userToken != null && userToken.getUserId().equals(id)) {
                userService.addFriend(id, friendId, remark);
                result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), null);
            } else {
                result = new ResultDto(ResultStatus.FAILED.getValue(), "token 错咯", null);
            }
        } catch (Exception e) {
            logger.error("", e);
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }
        return result;
    }

    @ApiOperation("同意验证好友")
    @RequestMapping(value = "/{id}/friend/accept/{friendId}", method = RequestMethod.POST)
    public ResultDto acceptFriend(
            @RequestHeader("token") String token,
            @PathVariable Long id,
            @RequestParam Long friendId
    ) {
        ResultDto result;
        try {
            UserToken userToken = userTokenService.getUserTokenByToken(token);
            if (userToken != null && userToken.getUserId().equals(id)) {
                userService.acceptFriend(id, friendId);
                result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), null);
            } else {
                result = new ResultDto(ResultStatus.FAILED.getValue(), "token 错咯", null);
            }
        } catch (Exception e) {
            logger.error("", e);
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }

        return result;
    }

    @ApiOperation("获取所有好友")
    @RequestMapping(value = "/{id}/friend", method = RequestMethod.GET)
    public ResultDto getFriends(
            @RequestHeader("token") String token,
            @PathVariable Long id
    ) {
        ResultDto result;
        try {
            UserToken userToken = userTokenService.getUserTokenByToken(token);
            if (userToken != null && userToken.getUserId().equals(id)) {
                result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(), ResultStatus.SUCCESSFUL.name(), userService.getFriends(id));
            } else {
                result = new ResultDto(ResultStatus.FAILED.getValue(), "token 错咯", null);
            }
        } catch (Exception e) {
            logger.error("", e);
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }

        return result;
    }
}
