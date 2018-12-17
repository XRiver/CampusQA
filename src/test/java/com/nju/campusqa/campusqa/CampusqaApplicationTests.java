package com.nju.campusqa.campusqa;

import com.nju.campusqa.campusqa.Service.UserService;
import com.nju.campusqa.campusqa.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CampusqaApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        User user = new User();
        user.setFollowProblem(Arrays.asList("1", "2", "4", "5"));
        user.setRole(1);
        mongoTemplate.save(user,"user");

        List<User> retrieved = mongoTemplate.findAll(User.class);
        retrieved.forEach(user1 -> {System.out.println(user1.getUserId());});
    }

    @Test
    public void testUserService() {
        Assert.assertEquals(userService.findOne("5c164bc65b1c7223540c3979").getUserId(), "5c164bc65b1c7223540c3979");
        Assert.assertEquals(userService.findOne("111123123123"), null);

    }

}

