package com.nju.campusqa.campusqa.Service;

import com.nju.campusqa.campusqa.entity.Comment;
import com.nju.campusqa.campusqa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiongzehua on 2018/12/18.
 */
@Service
public class CommentService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserService userService;

    public Comment findOne(String commentId) {
        Query q = Query.query(Criteria.where("commentId").is(commentId));
        return mongoTemplate.findOne(q, Comment.class);
    }

    public List<Comment> findByAnswerId(String answerId) {
        List<Comment> comments = mongoTemplate.find(Query.query(Criteria.where("answerId").is(answerId)), Comment.class);
        for (Comment c : comments) {
            User u = userService.findOne(c.getUserId());
            c.setUser(u);
        }
        return comments;
    }

}
