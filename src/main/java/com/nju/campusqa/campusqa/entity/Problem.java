package com.nju.campusqa.campusqa.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * Created by xiongzehua on 2018/12/15.
 */
@Document
public class Problem {
    @Id
    private String problemId;

    @Field
    private String title;

    @Field
    private String content;

    @Field
    private User user;

    @Field
    private Integer status; // 0:关闭 1:正常

    @Field
    private Integer anonymous; //  0:正常 1:匿名

    @Field
    private LocalDateTime createTime;

}
