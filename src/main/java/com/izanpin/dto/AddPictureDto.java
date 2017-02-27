package com.izanpin.dto;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by St on 2017/2/27.
 */
public class AddPictureDto {
    private Long userId;
    private String content;
    private ArrayList<String> imageUrls;

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

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
