package com.izanpin.service.impl;

import com.github.pagehelper.PageInfo;
import com.izanpin.dto.AddFeedbackDto;
import com.izanpin.entity.Feedback;
import com.izanpin.entity.User;
import com.izanpin.repository.FeedbackRepository;
import com.izanpin.service.FeedbackService;
import com.izanpin.service.ImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by St on 2017/5/25.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    ImageService imageService;

    @Autowired
    FeedbackRepository feedbackRepository;

    static Logger logger = LogManager.getLogger();

    @Override
    public void addFeedback(AddFeedbackDto dto) throws Exception {
        if (dto == null) {
            throw new Exception("参数错误");
        }

        if (dto.getContent() == null || dto.getContent().trim().isEmpty()) {
            throw new Exception("内容为空");
        }

        Feedback feedback = new Feedback(dto.getUserId(), dto.getContent(), dto.getDevice());
        feedbackRepository.add(feedback);

        List<MultipartFile> images = dto.getImages();
        if (images != null && !images.isEmpty()) {
            images.forEach((image) -> {
                try {
                    imageService.addFeedbackImage(image, feedback.getId());
                } catch (Exception e) {
                    logger.error("", e);
                }
            });
        }
    }

    @Override
    public PageInfo<Feedback> getFeedbacks(Integer page, Integer size) {
        return null;
    }
}
