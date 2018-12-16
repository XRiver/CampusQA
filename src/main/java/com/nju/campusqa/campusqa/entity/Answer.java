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
public class Answer {
    @Id
    private String answerId;

    @Field
    private String content;

    @Field
    private Integer anonymous; // 0:正常 1:匿名

    @Field
    private Integer problemId;

    @Field
    private User user; // 作者

    @Field
    private List<String> staredBy; // 用户微信号的列表

    @Field
    private LocalDateTime createTime;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Integer anonymous) {
        this.anonymous = anonymous;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getStaredBy() {
        return staredBy;
    }

    public void setStaredBy(List<String> staredBy) {
        this.staredBy = staredBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
