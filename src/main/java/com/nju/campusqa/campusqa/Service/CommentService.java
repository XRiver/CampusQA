package com.nju.campusqa.campusqa.Service;

import com.nju.campusqa.campusqa.entity.Comment;
import com.nju.campusqa.campusqa.entity.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * Created by xiongzehua on 2018/12/18.
 */
@Service
public class CommentService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Comment findOne(String commentId) {
        Query q = Query.query(Criteria.where("commentId").is(commentId));
        return mongoTemplate.findOne(q, Comment.class);
    }
}
