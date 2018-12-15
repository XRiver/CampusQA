package com.nju.campusqa.campusqa.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by xiongzehua on 2018/12/15.
 */
public class User {
    private String id;

    private String code;

    private List<Integer> followProblem;

    private List<Integer> followUser;

    private Integer role;

    private LocalDateTime dateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Integer> getFollowProblem() {
        return followProblem;
    }

    public void setFollowProblem(List<Integer> followProblem) {
        this.followProblem = followProblem;
    }

    public List<Integer> getFollowUser() {
        return followUser;
    }

    public void setFollowUser(List<Integer> followUser) {
        this.followUser = followUser;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
