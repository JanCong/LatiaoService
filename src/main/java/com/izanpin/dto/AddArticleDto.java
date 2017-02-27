package com.izanpin.dto;

import com.izanpin.common.util.Html;
import com.izanpin.enums.ArticleType;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by St on 2017/2/27.
 */
public class AddArticleDto {
    private Long userId;
    private String content;
    private ArticleType articleType;
    private ArrayList<String> imageUrls;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return Html.htmlUnescape(content);
    }

    public void setContent(String content) {
        this.content = Html.htmlUnescape(content);
    }

    public ArticleType getArticleType() {
        return articleType;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
