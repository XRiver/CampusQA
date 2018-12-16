package com.nju.campusqa.campusqa.Service;

import com.nju.campusqa.campusqa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by xiongzehua on 2018/12/16.
 */
@Service
public class UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public User signUp(String openId) {
        User user = new User();
        user.setOpenId(openId);
        user.setDateTime(LocalDateTime.now());
        Criteria criteria = Criteria.where("openId").is(openId);
        Query query = Query.query(criteria);
        User ret = mongoTemplate.findOne(query, User.class);
        if (ret == null)
            return mongoTemplate.save(user,"user");
        else
            return  ret;

    }
}
