package com.izanpin.controller.api;

import com.izanpin.dto.AddArticleDto;
import com.izanpin.dto.AddFeedbackDto;
import com.izanpin.dto.ResultDto;
import com.izanpin.entity.UserToken;
import com.izanpin.enums.ResultStatus;
import com.izanpin.service.FeedbackService;
import com.izanpin.service.UserTokenService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by St on 2017/5/25.
 */
@Api("反馈")
@RestController
@RequestMapping("/api/feedback")
public class FeedbackApiController {
    @Autowired
    FeedbackService feedbackService;
    @Autowired
    UserTokenService userTokenService;

    static Logger logger = LogManager.getLogger();

    @ApiOperation("新增反馈")
    @RequestMapping(method = RequestMethod.POST)
    public ResultDto addFeedback(
            @RequestHeader(value = "token", required = false) String token,
            @ApiParam("用户ID") @RequestParam(required = false) Long userId,
            @ApiParam("内容") @RequestParam() String content,
            @ApiParam("设备") @RequestParam(required = false) String device,
            @ApiParam("上传图片数组") @RequestParam(required = false) List<MultipartFile> images) {
        ResultDto result;
        try {
            UserToken userToken = userTokenService.getUserTokenByToken(token);
            if (userToken != null && userToken.getUserId().equals(userId) || userId == null) {
                AddFeedbackDto dto = new AddFeedbackDto(userId, content, device, images);
                feedbackService.addFeedback(dto);
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
}
