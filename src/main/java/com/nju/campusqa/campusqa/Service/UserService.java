package com.nju.campusqa.campusqa.Service;

import com.nju.campusqa.campusqa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDateTime;

/**
 * Created by xiongzehua on 2018/12/16.
 */
@Service
public class UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public User findOne(String userId) {
        Query q = Query.query(Criteria.where("id").is(userId));
        return mongoTemplate.findOne(q, User.class);
    }

}
