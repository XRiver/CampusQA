package com.nju.campusqa.campusqa;

import com.nju.campusqa.campusqa.controller.ProblemController;
import com.nju.campusqa.campusqa.entity.Answer;
import com.nju.campusqa.campusqa.entity.Problem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by xiongzehua on 2018/12/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProblemTest {

    @Autowired
    ProblemController problemController;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void testCreate() {
        Problem problem = new Problem();
        problem.setTitle("早恋都是怎样被家长发现的？");
        problem.setContent("如题？");
        problem.setUserId("5c164bc65b1c7223540c3979");
        problem.setStatus(1);
        problemController.create(problem);
    }

    @Test
    public void test2() {
        Query q = Query.query(Criteria.where("_id").is("5c17bba85b1c721140526181"));
        Answer answers = mongoTemplate.findOne(q, Answer.class);
        System.out.println(answers.getCreateTime());
}
}
