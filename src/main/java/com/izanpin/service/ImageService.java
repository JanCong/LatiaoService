package com.izanpin.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Smart on 2017/1/30.
 */
public interface ImageService {
    void AddImage(String url, long articalId);

    void AddImage(MultipartFile image, long articalId) throws Exception;
}
