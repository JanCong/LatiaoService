package com.izanpin.dto;

import com.izanpin.common.util.Html;
import com.izanpin.enums.ArticleType;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by St on 2017/2/27.
 */
public class AddArticleDto {
    private Long userId;
    private String content;
    private List<String> imageUrls;

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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
