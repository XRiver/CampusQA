package com.nju.campusqa.campusqa.controller;

import com.nju.campusqa.campusqa.Service.AnswerService;
import com.nju.campusqa.campusqa.Service.ProblemService;
import com.nju.campusqa.campusqa.Service.UserService;
import com.nju.campusqa.campusqa.entity.Answer;
import com.nju.campusqa.campusqa.entity.Comment;
import com.nju.campusqa.campusqa.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

/**
 * Created by xiongzehua on 2018/12/18.
 */
@Controller
public class CommentController {

    @Autowired
    private UserService userService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/api/comment/create")
    @ResponseBody
    public Response create(@RequestBody Comment comment) {

        if (problemService.findOne(comment.getProblemId()) == null
                || userService.findOne(comment.getUserId()) == null
                || answerService.findOne(comment.getAnswerId()) == null)
            return Response.createByIllegalArgument(null);

        comment.setCreateTime(LocalDateTime.now());
        mongoTemplate.save(comment, "comment");
        return Response.createBySuccess(null);
    }


}
