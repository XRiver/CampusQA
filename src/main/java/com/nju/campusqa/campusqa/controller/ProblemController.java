package com.nju.campusqa.campusqa.controller;

import com.nju.campusqa.campusqa.entity.Problem;
import com.nju.campusqa.campusqa.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xiongzehua on 2018/12/16.
 */
public class ProblemController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/api/post/create")
    public Response create(@RequestBody Problem problem) {
        return Response.createByBan(null);
    }
}
