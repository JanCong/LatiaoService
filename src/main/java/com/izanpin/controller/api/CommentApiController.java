package com.izanpin.controller.api;

import com.github.pagehelper.PageInfo;
import com.izanpin.data.ImportData;
import com.izanpin.dto.AddCommentDto;
import com.izanpin.entity.Article;
import com.izanpin.entity.Comment;
import com.izanpin.service.ArticleService;
import com.izanpin.service.CommentService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Console;

/**
 * Created by pengyuancong on 2017/1/29.
 */
@Api(value = "评论")
@RestController
@RequestMapping(value = "/api/comment")
public class CommentApiController {
    @Autowired
    CommentService commentService;

    @ApiOperation(value = "获取评论")
    @RequestMapping(value = "/{articleId}/{page}/{size}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo getComments(@ApiParam(value = "无聊图/段子 ID") @PathVariable Long articleId, @ApiParam(value = "页码") @PathVariable Integer page, @ApiParam(value = "页大小") @PathVariable Integer size) {
        return commentService.getComments(articleId, page, size);
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
    public void addComment(@ApiParam(value = "无聊图/段子 ID") @PathVariable Long articleId,
                           @ApiParam(value = "评论内容") @RequestBody AddCommentDto dto) {
        commentService.addComment(articleId, dto.getUserId(), dto.getContent());
    }

    @ApiOperation(value = "回复评论")
    @RequestMapping(value = "/reply/{replyToId}", method = RequestMethod.POST)
    @ResponseBody
    public void reply(@ApiParam(value = "评论ID") @PathVariable Long replyToId,
                      @ApiParam(value = "回复内容") @RequestBody AddCommentDto dto) {
        commentService.reply(replyToId, dto.getUserId(), dto.getContent());
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