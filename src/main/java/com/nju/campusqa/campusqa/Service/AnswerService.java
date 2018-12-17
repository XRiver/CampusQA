package com.nju.campusqa.campusqa.Service;

import com.nju.campusqa.campusqa.entity.Answer;
import com.nju.campusqa.campusqa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiongzehua on 2018/12/16.
 */
@Service
public class AnswerService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserService userService;

    public List<Answer> findByProblemId(String problemId) {
        // Query q = Query.query(Criteria.where("id").is(userId));
        return null; //TODO
    }

}
