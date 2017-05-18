package com.izanpin.repository;

import com.izanpin.dto.RequestArticleTimelineDto;
import com.izanpin.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
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

    @ResultMap("ResultMap")
    @Select("select article.*,\n" +
            "       image.id as image_id,\n" +
            "       image.article_id,\n" +
            "       image.url,\n" +
            "       image.thumbnail_url,\n" +
            "       image.is_video,\n" +
            "       image.create_time as image_create_time\n" +
            "  from article\n" +
            "  left join image on article.id= image.article_id\n" +
            " where week(article.create_time)= week(now())\n" +
            " order by rand()")
    List<Article> findByRandomInWeek();

    void increaseCommentCount(@Param("id") Long id, @Param("count") Integer count);

    void increaseLikeCount(@Param("id") Long id, @Param("count") Integer count);

    void increaseHateCount(@Param("id") Long id, @Param("count") Integer count);
}