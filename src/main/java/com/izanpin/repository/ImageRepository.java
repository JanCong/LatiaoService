package com.izanpin.repository;

import com.izanpin.entity.Image;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository {
    int deleteByPrimaryKey(Long id);

    int insert(Image record);

    int insertSelective(Image record);

    Image selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Image record);

    int updateByPrimaryKey(Image record);
}