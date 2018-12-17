package com.nju.campusqa.campusqa.controller;

import com.mongodb.client.result.UpdateResult;
import com.nju.campusqa.campusqa.Service.UserService;
import com.nju.campusqa.campusqa.entity.Answer;
import com.nju.campusqa.campusqa.entity.Comment;
import com.nju.campusqa.campusqa.entity.User;
import com.nju.campusqa.campusqa.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xiongzehua on 2018/12/16.
 */
@Controller
public class AnswerController {

    @Autowired
    private UserService userService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @ResponseBody
    @PostMapping("/api/answer/create")
    public Response<Object> createAnswer(@RequestBody Map<String, Object> params) {

        // TODO use Answer to take params
        String userId = (String) params.get("userId"),
                content = (String) params.get("content"),
                problemId = (String) params.get("problemId");
        Integer anonymous = (Integer) params.get("anonymous");

        User user = userService.findOne(userId);
        if (user == null || (user.getBan() != null && user.getBan().isAfter(LocalDateTime.now()))) {
            return Response.createByBan(null);
        }

        Answer newAnswer = new Answer();
        newAnswer.setAnonymous(anonymous);
        newAnswer.setContent(content);
        newAnswer.setCreateTime(LocalDateTime.now());
        newAnswer.setUserId(userId);
        newAnswer.setProblemId(problemId);
        newAnswer.setStaredBy(new ArrayList<String>());

        Answer ret = mongoTemplate.save(newAnswer, "answer");

        if (ret != null) {
            return Response.createBySuccess(null);
        } else {
            return Response.createByIllegalArgument(null);
        }
    }

    class AnswerCommentListTuple {
        private Answer answer;
        private List<Comment> commentList;

        public AnswerCommentListTuple(Answer answer, List<Comment> comments) {
            this.answer = answer;
            this.commentList = comments;
        }

        public Answer getAnswer() {
            return answer;
        }

        public void setAnswer(Answer answer) {
            this.answer = answer;
        }

        public List<Comment> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<Comment> commentList) {
            this.commentList = commentList;
        }
    }

    @ResponseBody
    @GetMapping("/api/answer/list")
    public Response<ArrayList<AnswerCommentListTuple>> listAnswers(@RequestParam(name = "problemId") String problemId) {

        List<Answer> answers = mongoTemplate.find(Query.query(Criteria.where("problemId").is(problemId)), Answer.class);
        ArrayList<AnswerCommentListTuple> ret = new ArrayList<>();
        for (Answer a : answers) {
            // DONE Answer要加上用户！使用AnswerService吧
            User user = userService.findOne(a.getUserId());
            a.setUser(user);

            List<Comment> comments = mongoTemplate.find(Query.query(Criteria.where("answerId").is(a.getAnswerId())), Comment.class);
            ret.add(new AnswerCommentListTuple(a, comments));
        }

        return Response.createBySuccess(ret);
    }

    @ResponseBody
    @PostMapping("/api/answer/mylist")
    public Response<ArrayList<AnswerCommentListTuple>> listMyAnswers(@RequestBody Map<String, Object> params) {
        String userId = (String) params.get("userId");

        User user = userService.findOne(userId);

        List<Answer> answers = mongoTemplate.find(Query.query(Criteria.where("userId").is(userId)), Answer.class);
        ArrayList<AnswerCommentListTuple> ret = new ArrayList<>();
        for (Answer a : answers) {
            a.setUser(user);
            List<Comment> comments = mongoTemplate.find(Query.query(Criteria.where("answerId").is(a.getAnswerId())), Comment.class);
            ret.add(new AnswerCommentListTuple(a, comments)); //TODO 此处comment应该加上user信息，应当由CommentService处理
        }

        return Response.createBySuccess(ret);
    }

    @ResponseBody
    @PostMapping("/api/answer/delete")
    public Response<Object> deleteAnswer(@RequestBody Map<String, Object> params) {
        String userId = (String) params.get("userId"),
                answerId = (String) params.get("answerId");

        User user = userService.findOne(userId);
        List<Answer> answers = mongoTemplate.find(Query.query(Criteria.where("id").is(answerId)), Answer.class);

        if (answers.isEmpty()) {
            return Response.createByIllegalArgument(null);
        }
        Answer answer = answers.get(0);
        if (answer.getUserId().equals(userId) || user.getRole() == 1) { // Belong to the user or user is admin
            mongoTemplate.remove(Query.query(Criteria.where("answerId").is(answerId)), Comment.class);
            mongoTemplate.remove(answer);

            return Response.createBySuccess(null);
        } else {
            return Response.createByNeedAuthority(null);
        }
    }

    @ResponseBody
    @PostMapping("/api/answer/star")
    public Response<Object> starAnswer(@RequestBody Map<String, Object> params) {
        String userId = (String) params.get("userId"),
                answerId = (String) params.get("answerId");

        User user = userService.findOne(userId);

        if (user == null || (user.getBan() != null && user.getBan().isAfter(LocalDateTime.now()))) {
            return Response.createByBan(null);
        }

        List<Answer> answers = mongoTemplate.find(Query.query(Criteria.where("id").is(answerId)), Answer.class);

        if (answers.isEmpty()) {
            return Response.createByIllegalArgument(null);
        }
        Answer answer = answers.get(0);
        if (!answer.getStaredBy().contains(userId)) {
            Criteria critUpdate = Criteria.where("id").is(answerId);
            Query queryUpdate = Query.query(critUpdate);
            List<String> starList = answer.getStaredBy();
            starList.add(userId);
            Update update = Update.update("staredBy", starList);

            UpdateResult updateRet = mongoTemplate.updateFirst(queryUpdate, update, Answer.class);
        }
        return Response.createBySuccess(null);
    }

    //TODO 获取最新动态--一堆Answer
}
