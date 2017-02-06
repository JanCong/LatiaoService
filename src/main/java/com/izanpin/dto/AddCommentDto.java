package com.izanpin.dto;

/**
 * Created by St on 2017/2/6.
 */
public class AddCommentDto {
    private Long userId;
    private String content;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
