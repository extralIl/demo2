package com.extra.demo.controller;

import com.extra.demo.bean.User;
import com.extra.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecureController {

    @Autowired
    UserService userService;


    @RequestMapping("/secure/getUserInfo")
    @ResponseBody
    public User getUserInfo(String phoneNumber){
        User user = userService.getUserByPhoneNumber(phoneNumber);
        return user;
    }


}
