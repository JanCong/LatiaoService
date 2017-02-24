package com.izanpin.repository;

import com.izanpin.dto.RequestArticleTimelineDto;
import com.izanpin.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository {
    List<Article> find(@Param("keyword") String keyword);

    int insertSelective(Article record);

    Article getByHashId(String hashId);

    Article get(Long id);

    List<Article> findByType(@Param("type") Integer value, @Param("keyword") String keyword);

    List<Article> findByTimeline(RequestArticleTimelineDto dto);

    void increaseCommentCount(Long id, Integer count);
}