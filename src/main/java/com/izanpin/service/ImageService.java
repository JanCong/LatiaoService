package com.izanpin.service;

import com.izanpin.entity.Image;
import com.izanpin.entity.User;

import java.util.List;

/**
 * Created by Smart on 2017/1/30.
 */
public interface ImageService {
    void AddImage(String url, long articalId);
}
