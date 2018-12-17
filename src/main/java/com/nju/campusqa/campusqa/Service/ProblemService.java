package com.nju.campusqa.campusqa.Service;

import com.mongodb.client.result.UpdateResult;
import com.nju.campusqa.campusqa.entity.Problem;
import com.nju.campusqa.campusqa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * Created by xiongzehua on 2018/12/18.
 */
@Service
public class ProblemService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Problem findOne(String problemId) {
        Query q = Query.query(Criteria.where("problemId").is(problemId));
        return mongoTemplate.findOne(q, Problem.class);
    }

}
