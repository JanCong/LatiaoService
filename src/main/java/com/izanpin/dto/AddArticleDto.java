package com.izanpin.dto;

import com.izanpin.common.util.Html;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by St on 2017/2/27.
 */
public class AddArticleDto {
    public AddArticleDto(Long userId, String content, String device, List<String> imageUrls, List<MultipartFile> images) {
        this.setUserId(userId);
        this.setContent(content);
        this.setDevice(device);
        this.setImageUrls(imageUrls);
        this.setImages(images);
    }

    private Long userId;
    private String content;
    private String device;
    private List<String> imageUrls;
    private List<MultipartFile> images;

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

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
