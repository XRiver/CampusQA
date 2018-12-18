package com.nju.campusqa.campusqa.controller;

import com.mongodb.client.result.DeleteResult;
import com.nju.campusqa.campusqa.Service.AnswerService;
import com.nju.campusqa.campusqa.Service.CommentService;
import com.nju.campusqa.campusqa.Service.ProblemService;
import com.nju.campusqa.campusqa.Service.UserService;
import com.nju.campusqa.campusqa.entity.Answer;
import com.nju.campusqa.campusqa.entity.Comment;
import com.nju.campusqa.campusqa.entity.Problem;
import com.nju.campusqa.campusqa.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    private CommentService commentService;

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

    @PostMapping("/xxx/api/comment/delete")
    @ResponseBody
    public Response delete(@RequestBody Comment param) {

        if (userService.findOne(param.getUserId()) == null)
            return Response.createByIllegalArgument(null);

        if (!commentService.findOne(param.getProblemId()).getUserId().equals(param.getUserId()))
            return Response.createByNeedAuthority(null);

        Criteria criteria = Criteria.where("commentId").is(param.getCommentId());
        Query query = Query.query(criteria);
        DeleteResult result = mongoTemplate.remove(query, Comment.class);
        return Response.createBySuccess(null);
    }


}
