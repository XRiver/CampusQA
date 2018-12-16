package com.nju.campusqa.campusqa.controller;

import com.mongodb.client.result.UpdateResult;
import com.nju.campusqa.campusqa.Service.UserService;
import com.nju.campusqa.campusqa.entity.User;
import com.nju.campusqa.campusqa.vo.Response;
import com.nju.campusqa.campusqa.vo.SignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by xiongzehua on 2018/12/16.
 */
@Controller
public class UserController {

    public static final String NICKNAME_COL = "nickName";
    public static final String AVATAR_COL = "avatarUrl";
    public static final String ROLE_COL = "role";

    @Autowired
    UserService userService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @ResponseBody
    @PostMapping("/api/signup")
    public Response<User> signUp(@RequestBody Map<String, Object> params) {

        String code = (String) params.get("code"),
                avatarUrl = (String) params.get(AVATAR_COL),
                nickName = (String) params.get(NICKNAME_COL);

        RestTemplate restTemplate = new RestTemplate();
        String str = restTemplate.getForObject("https://api.weixin.qq.com/sns/jscode2session?appid=wx8271f" +
                "84618387e52&secret=31cb337d5e72dc4c1eb3194a1e6caef4&js_code={code}&grant_type=authorizatio" +
                "n_code", String.class, code);
        String[] strs = str.split("\"openid\":\"");
        String openId = strs[1].replace("\"}", "");

        Criteria criteria = Criteria.where("openId").is(openId);
        Query query = Query.query(criteria);
        User queryRet = mongoTemplate.findOne(query, User.class);

        if (queryRet == null) { // Create new user

            User newUser = new User();
            newUser.setOpenId(openId);
            newUser.setAvatarUrl(avatarUrl);
            newUser.setNickName(nickName);
            newUser.setFollowProblem(new ArrayList<String>());
            newUser.setFollowUser(new ArrayList<String>());
            newUser.setRole(0);
            newUser.setBan(null);

            queryRet = mongoTemplate.save(newUser, "user");

        } else { // Update nickName and avatarUrl

            Criteria critUpdate = Criteria.where("id").is(queryRet.getId());
            Query queryUpdate = Query.query(critUpdate);
            Update update = Update.update(AVATAR_COL, avatarUrl);
            update.set(NICKNAME_COL, nickName);

            UpdateResult updateRet = mongoTemplate.updateFirst(queryUpdate, update, User.class);
            queryRet = mongoTemplate.findOne(query, User.class); // Re-query the User object.

        }

        return Response.createBySuccess(queryRet);
    }

    @ResponseBody
    @PostMapping("/api/authorization")
    public Response<Object> grantAdmin(@RequestBody Map<String, Object> params) {

        String userId = (String) params.get("userId"),
                password = (String) params.get("password");

        if("888888".equals(password)) {
            if(userService.findOne(userId) != null) {

                Criteria critUpdate = Criteria.where("id").is(userId);
                Query queryUpdate = Query.query(critUpdate);
                Update update = Update.update(ROLE_COL,1);

                UpdateResult updateRet = mongoTemplate.updateFirst(queryUpdate, update, User.class);
            }

            return Response.createBySuccess(null);
        } else {
            return Response.createByNeedAuthority(null);
        }
    }

}
