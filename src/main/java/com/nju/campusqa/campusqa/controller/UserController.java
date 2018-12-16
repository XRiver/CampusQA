package com.nju.campusqa.campusqa.controller;

import com.nju.campusqa.campusqa.Service.UserService;
import com.nju.campusqa.campusqa.entity.User;
import com.nju.campusqa.campusqa.vo.Response;
import com.nju.campusqa.campusqa.vo.SignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * Created by xiongzehua on 2018/12/16.
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @ResponseBody
    @GetMapping("/api/signup")
    public User signUp(String code) {
        System.out.println("into /api/signup");
        System.out.println(code);
        RestTemplate restTemplate = new RestTemplate();
        String str =  restTemplate.getForObject("https://api.weixin.qq.com/sns/jscode2session?appid=wx8271f84618387e52&secret=31cb337d5e72dc4c1eb3194a1e6caef4&js_code={code}&grant_type=authorization_code", String.class, code);
        String[] strs = str.split("\"openid\":\"");
        String openId = strs[1].replace("\"}", "");
        System.out.println(openId);

        return userService.signUp(openId);
    }

    @RequestMapping("/api/hello")
    @ResponseBody
    public Response hello() {
        System.out.println("into hello");
        return Response.createBySuccess(null);
    }
}
