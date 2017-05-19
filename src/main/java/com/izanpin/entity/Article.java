package com.izanpin.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.izanpin.common.util.Html;
import com.izanpin.common.util.SnowFlake;
import com.izanpin.enums.ArticleStatus;
import com.izanpin.enums.ArticleType;
import org.apache.ibatis.jdbc.Null;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class Article {
    public Article() {
        SnowFlake snowFlake = new SnowFlake(0, 0);
        this.setId(snowFlake.nextId());
        this.setCommentCount(0);
        this.setLikeCount(0);
        this.setHateCount(0);
        this.setStatus(ArticleStatus.NORMAL.getValue());
        this.setCreateTime(new Date());
        this.setUpdateTime(new Date());
    }

    public Article(String content, Long authorId, String authorName, String authorAvatar, String device) {
        this();
        this.setContent(content);
        this.setAuthorId(authorId);
        this.setAuthorName(authorName);
        this.setAuthorAvatar(authorAvatar);
        this.setDevice(device);
    }

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String content;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;

    private String authorName;

    private String authorAvatar;

    private String hashId;

    private String device;

    private Integer commentCount;

    private Integer likeCount;

    private Integer hateCount;

    private Integer type;

    private Integer status;

    private String ip;

    private Date createTime;

    private Date updateTime;

    private List<Image> images;

    private List<Like> likes;

    private Boolean Liked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        if (content == null) {
            return "";
        }
        return Html.htmlUnescape(content);
    }

    public void setContent(String content) {
        this.content = content == null ? "" : content.trim();
        this.content = Html.htmlUnescape(this.content);
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName == null ? null : authorName.trim();
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar == null ? null : authorAvatar.trim();
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId == null ? null : hashId.trim();
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getHateCount() {
        return hateCount;
    }

    public void setHateCount(Integer hateCount) {
        this.hateCount = hateCount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public Boolean getLiked() {
        return Liked;
    }

    public void setLiked(Long userId) {
        if (userId == null) {
            this.Liked = false;
        } else {
            this.Liked = this.likes.stream().anyMatch(l -> userId.equals(l.getUserId()));
        }
    }
}