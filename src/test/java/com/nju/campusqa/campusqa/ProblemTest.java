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

}
