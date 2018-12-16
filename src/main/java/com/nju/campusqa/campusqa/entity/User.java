package com.nju.campusqa.campusqa.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by xiongzehua on 2018/12/15.
 */
@Document
public class User {
    @Id
    private String id;

    @Field
    private String openId;

    @Field
    private String nickName;

    @Field
    private String avatarUrl;

    @Field
    private List<String> followProblem;

    @Field
    private List<String> followUser;

    @Field
    private Integer role;

    @Field
    private LocalDateTime ban;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<String> getFollowProblem() {
        return followProblem;
    }

    public void setFollowProblem(List<String> followProblem) {
        this.followProblem = followProblem;
    }

    public List<String> getFollowUser() {
        return followUser;
    }

    public void setFollowUser(List<String> followUser) {
        this.followUser = followUser;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public LocalDateTime getBan() {
        return ban;
    }

    public void setBan(LocalDateTime ban) {
        this.ban = ban;
    }
}
