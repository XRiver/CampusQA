package com.nju.campusqa.campusqa.controller;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.nju.campusqa.campusqa.Service.ProblemService;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xiongzehua on 2018/12/16.
 */
@Controller
public class ProblemController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private ProblemService problemService;

    @PostMapping("/api/problem/create")
    @ResponseBody
    public Response create(@RequestBody Problem problem) {
        String userId = problem.getUserId();
        User user = userService.findOne(userId);

        if (user == null)
            return Response.createByIllegalArgument(null);

        if (user.getBan() != null && user.getBan().isAfter(LocalDateTime.now()))
            return  Response.createByBan(null);

        problem.setCreateTime(LocalDateTime.now());
        mongoTemplate.save(problem, "problem");
        return  Response.createBySuccess(null);
    }

    @GetMapping("/api/problem/list")
    @ResponseBody
    public Response list() {
        System.out.println("into /api/problem/list");
        List<Problem> list = mongoTemplate.findAll(Problem.class);
        for (Problem p : list) {
            User user = userService.findOne(p.getUserId());
            p.setUser(user);
        }
        return Response.createBySuccess(list);
    }

    @PostMapping("/api/problem/mylist")
    @ResponseBody
    public Response mylist(@RequestBody HashMap params) {
        String userId = (String) params.get("userId");
        System.out.println("into /api/problem/mylist");

        User user = userService.findOne(userId);
        if (user == null)
            return Response.createByIllegalArgument(null);

        List<Problem> list = mongoTemplate.find(Query.query(Criteria.where("userId").is(userId)), Problem.class);
        for (Problem p : list) {
            p.setUser(user);
        }

        return Response.createBySuccess(list);
    }

    @PostMapping("/api/problem/follow")
    @ResponseBody
    public Response follow(@RequestBody Problem problem) {
        System.out.println("into /api/problem/follow");

        User user = userService.findOne(problem.getUserId());
        if (user == null)
            return Response.createByIllegalArgument(null);

        List followList = user.getFollowProblem();
        if (!followList.contains(problem.getProblemId()))
            followList.add(problem.getProblemId());


        Criteria criteria = Criteria.where("userId").is(problem.getUserId());
        Query query = Query.query(criteria);
        Update update = Update.update("followProblem", followList);
        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);

        return Response.createBySuccess(null);
    }

    @PostMapping("/api/problem/unfollow")
    @ResponseBody
    public Response unfollow(@RequestBody Problem problem) {
        System.out.println("into /api/problem/unfollow");

        User user = userService.findOne(problem.getUserId());
        if (user == null)
            return Response.createByIllegalArgument(null);

        List followList = user.getFollowProblem();
        if (followList.contains(problem.getProblemId()))
            followList.remove(problem.getProblemId());

        Criteria criteria = Criteria.where("userId").is(problem.getUserId());
        Query query = Query.query(criteria);
        Update update = Update.update("followProblem", followList);
        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);

        return Response.createBySuccess(null);
    }

    @PostMapping("/api/problem/close")
    @ResponseBody
    public Response close(@RequestBody Problem param) {
        System.out.println("into /api/problem/close");
        String userId = param.getUserId();
        String problemId = param.getProblemId();

        Problem problem = problemService.findOne(problemId);
        if (problem == null)
            return Response.createByIllegalArgument(null);
        if (!problem.getUserId().equals(userId))
            return Response.createByNeedAuthority(null);

        Criteria criteria = Criteria.where("_id").is(problemId);
        Query query = Query.query(criteria);
        Update update = Update.update("status", 0);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Problem.class);

        return Response.createBySuccess(null);
    }

    @PostMapping("/api/problem/delete")
    @ResponseBody
    public Response delete(@RequestBody Problem param) {
        System.out.println("into /api/problem/delete");

        String userId = param.getUserId();
        String problemId = param.getProblemId();

        Problem problem = problemService.findOne(problemId);
        if (userService.findOne(userId).getRole() == 0)
            return Response.createByNeedAuthority(null);

        Criteria criteria = Criteria.where("problemId").is(problemId);
        Query query = Query.query(criteria);
        DeleteResult result = mongoTemplate.remove(query, Problem.class);

        return Response.createBySuccess(null);
    }


    @GetMapping("/api/problem/search")
    @ResponseBody
    public Response search(String key) {
        System.out.println(key);
        Criteria criteria = Criteria.where("title").regex(key);
        Query query = Query.query(criteria);
        List<Problem> list = mongoTemplate.find(query, Problem.class);

        for (Problem p : list) {
            User user = userService.findOne(p.getUserId());
            p.setUser(user);
        }
        return Response.createBySuccess(list);
    }

}
