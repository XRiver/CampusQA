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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Created by xiongzehua on 2018/12/16.
 */
@Controller
public class UserController {

    public static final String NICKNAME_COL = "nickName";
    public static final String AVATAR_COL = "avatarUrl";

    @Autowired
    UserService userService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @ResponseBody
    @GetMapping("/api/signup")
    public Response<User> signUp(@RequestBody Map<String,Object>params) {

        String code = (String)params.get("code"),
                avatarUrl = (String)params.get(AVATAR_COL),
                nickName = (String)params.get(NICKNAME_COL);

        RestTemplate restTemplate = new RestTemplate();
        String str =  restTemplate.getForObject("https://api.weixin.qq.com/sns/jscode2session?appid=wx8271f84618387e52&secret=31cb337d5e72dc4c1eb3194a1e6caef4&js_code={code}&grant_type=authorization_code", String.class, code);
        String[] strs = str.split("\"openid\":\"");
        String openId = strs[1].replace("\"}", "");

        Criteria criteria = Criteria.where("openId").is(openId);
        Query query = Query.query(criteria);
        User queryRet = mongoTemplate.findOne(query, User.class);

        if (queryRet == null){ // Create new user
            User newUser = new User();
            newUser.setOpenId(openId);
            newUser.setDateTime(LocalDateTime.now());
            newUser.setAvatarUrl(avatarUrl);
            newUser.setNickName(nickName);

            queryRet =  mongoTemplate.save(newUser,"user");
        } else { // Update nickName and avatarUrl
            Criteria critUpdate = Criteria.where("id").is(queryRet.getId());
            Query queryUpdate = Query.query(critUpdate);
            Update update = Update.update(AVATAR_COL,avatarUrl);
            update.set(NICKNAME_COL,nickName);

            UpdateResult updateRet = mongoTemplate.updateFirst(query,update,User.class);
            queryRet = mongoTemplate.findOne(query, User.class); // Re-query the User object.
        }

        return Response.createBySuccess(queryRet);
    }

}
