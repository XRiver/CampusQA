package com.nju.campusqa.campusqa.controller;

import com.mongodb.client.result.UpdateResult;
import com.nju.campusqa.campusqa.Service.UserService;
import com.nju.campusqa.campusqa.entity.Answer;
import com.nju.campusqa.campusqa.entity.Problem;
import com.nju.campusqa.campusqa.entity.User;
import com.nju.campusqa.campusqa.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by xiongzehua on 2018/12/16.
 */
@Controller
public class AnswerController {


    @Autowired
    private MongoTemplate mongoTemplate;

    @ResponseBody
    @PostMapping("/api/answer/create")
    public Response<Object> createAnswer(@RequestBody Map<String, Object> params) {

        String userId = (String) params.get("userId"),
                content = (String) params.get("content"),
                problemId = (String) params.get("problemId");
        int anonymous = (Integer) params.get("anonymous");

        Answer newAnswer = new Answer();
        newAnswer.setAnonymous(anonymous);
        newAnswer.setContent(content);
        newAnswer.setCreateTime(LocalDateTime.now());
        newAnswer.setUserId(userId);
        newAnswer.setStaredBy(new ArrayList<String>());

        Answer ret = mongoTemplate.save(newAnswer,"answer");

        if(ret != null) {
            return Response.createBySuccess(null);
        } else {
            return Response.createByIllegalArgument(null);
        }
    }


}
