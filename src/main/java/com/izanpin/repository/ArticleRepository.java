package com.izanpin.repository;

import com.izanpin.entity.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository {
    List<Article> find();

    int insertSelective(Article record);

    Article getByHashId(String hashId);

    Article get(Long id);

    List<Article> findByType(Integer value);
}