package com.izanpin.service;

import com.izanpin.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Smart on 2017/1/30.
 */
public interface ImageService {
    void addImage(MultipartFile file, Long articleId) throws Exception;

    void addImage(String url, Long articleId, Boolean fromBSBDJ) throws Exception;

    void addImage(String url, Long articleId) throws Exception;

    Image addImage(MultipartFile file) throws Exception;

    void addFeedbackImage(MultipartFile file, Long feedbackId) throws Exception;
}
