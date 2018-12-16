package com.nju.campusqa.campusqa.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by xiongzehua on 2018/12/15.
 */
public class Answer {
    private String id;

    private String content;

    private Integer anonymous; // 0:正常 1:匿名

    private Integer problemId;

    private String userId; // 作者

    private List<String> staredBy; // 用户微信号的列表

    private LocalDateTime createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
