package com.izanpin.controller.api;

import com.github.pagehelper.PageInfo;
import com.izanpin.dto.ResultDto;
import com.izanpin.entity.Comment;
import com.izanpin.entity.UserToken;
import com.izanpin.enums.ResultStatus;
import com.izanpin.service.CommentService;
import com.izanpin.service.UserTokenService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by pengyuancong on 2017/1/29.
 */
@Api(value = "评论")
@RestController
@RequestMapping(value = "/api/comment")
public class CommentApiController {
    @Autowired
    CommentService commentService;
    @Autowired
    UserTokenService userTokenService;

    @ApiOperation(value = "获取评论")
    @RequestMapping(value = "/{articleId}/{page}/{size}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<PageInfo<Comment>> getComments(@ApiParam(value = "无聊图/段子 ID") @PathVariable Long articleId,
                                                    @ApiParam(value = "页码") @PathVariable Integer page,
                                                    @ApiParam(value = "页大小") @PathVariable Integer size) {
        ResultDto<PageInfo<Comment>> result;
        try {
            result = new ResultDto(ResultStatus.SUCCESSFUL.getValue(),
                    ResultStatus.SUCCESSFUL.name(),
                    commentService.getComments(articleId, page, size));
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResultDto(ResultStatus.FAILED.getValue(), e.getMessage(), null);
        }

        return result;
    }

    @ApiOperation(value = "根据ID获取")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Comment get(@PathVariable Long id) {
        return commentService.getById(id);
    }

    @ApiOperation(value = "添加评论")
    @RequestMapping(value = "/{articleId}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto addComment(
        @RequestHeader("token") String token,
        @ApiParam("无聊图/段子 ID") @PathVariable Long articleId,
        @ApiParam("用户id") @RequestParam Long userId,
        @ApiParam("评论内容") @RequestParam String content) {
        ResultDto result;
        try {
            UserToken userToken = userTokenService.getUserTokenByToken(token);
            if (userToken != null && userToken.getUserId().equals(userId)) {
                commentService.addComment(articleId, userId, content);
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

    @ApiOperation(value = "回复评论")
    @RequestMapping(value = "/reply/{replyToId}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto reply(
        @RequestHeader("token") String token,
        @ApiParam(value = "评论ID") @PathVariable Long replyToId,
        @ApiParam("用户id") @RequestParam Long userId,
        @ApiParam("评论内容") @RequestParam String content) {
        ResultDto result;
        try {
            UserToken userToken = userTokenService.getUserTokenByToken(token);
            if (userToken != null && userToken.getUserId().equals(userId)) {
                commentService.reply(replyToId, userId, content);
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

    @ApiOperation(value = "赞")
    @RequestMapping(value = "/like/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void like(@PathVariable Long id, @RequestBody(required = false) Long userId) {
        commentService.like(id, userId);
    }

    @ApiOperation(value = "踩")
    @RequestMapping(value = "/hate/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void hate(@PathVariable Long id, @RequestBody(required = false) Long userId) {
        commentService.hate(id, userId);
    }
}
