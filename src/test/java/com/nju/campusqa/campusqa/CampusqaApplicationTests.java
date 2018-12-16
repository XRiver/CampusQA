package com.nju.campusqa.campusqa;

import com.nju.campusqa.campusqa.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CampusqaApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void contextLoads() {
        User user = new User();
        user.setDateTime(LocalDateTime.now());
        user.setFollowProblem(Arrays.asList(1, 2, 4, 5));
        user.setRole(1);
        mongoTemplate.save(user, "user");
        Criteria
    }

}

