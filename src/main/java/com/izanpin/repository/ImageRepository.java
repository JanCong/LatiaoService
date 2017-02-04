package com.izanpin.repository;

import com.izanpin.entity.Image;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository {
    int add(Image record);

    Image selectByPrimaryKey(Long id);

}