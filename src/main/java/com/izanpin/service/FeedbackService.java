package com.izanpin.service;

import com.github.pagehelper.PageInfo;
import com.izanpin.dto.AddFeedbackDto;
import com.izanpin.entity.Feedback;

/**
 * Created by St on 2017/5/25.
 */
public interface FeedbackService {
    void addFeedback(AddFeedbackDto dto) throws Exception;

    PageInfo<Feedback> getFeedbacks(Integer page, Integer size);
}
