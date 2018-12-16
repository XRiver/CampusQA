package com.nju.campusqa.campusqa.entity;

import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

import java.time.LocalDateTime;

/**
 * Created by xiongzehua on 2018/12/15.
 */
public class Comment {
    private String id;

    private Integer problemId;

    private Integer answerId;

    private String content;

    private Integer anonymous; // 0:正常 1:匿名

    private LocalDateTime createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
