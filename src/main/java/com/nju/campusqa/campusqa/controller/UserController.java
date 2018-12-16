package com.nju.campusqa.campusqa.controller;

import com.nju.campusqa.campusqa.vo.SignUp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 * Created by xiongzehua on 2018/12/16.
 */
@Controller
public class UserController {

    @RequestMapping("/api/signup")
    public void signUp(String code) {
        System.out.println("into /api/signup");
        RestTemplate restTemplate = new RestTemplate();
        SignUp signUp =  restTemplate.getForObject("https://api.weixin.qq.com/sns/jscode2session?appid=wx8271f84618387e52&secret=31cb337d5e72dc4c1eb3194a1e6caef4&js_code={code}&grant_type=authorization_code", SignUp.class, code);
        String openId = signUp.getOpenid();
    }
}
