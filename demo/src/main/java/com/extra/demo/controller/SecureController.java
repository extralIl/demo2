package com.extra.demo.controller;

import com.extra.demo.bean.User;
import com.extra.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class SecureController {

    @Autowired
    UserService userService;



    @PostMapping(value = "/secure/getUserInfo")
    @ResponseBody
    public User getUserInfo(HttpServletRequest request, HttpServletResponse response){
        String phoneNumber = request.getParameter("phoneNumber");
        User user = userService.getUserByPhoneNumber(phoneNumber);
        return user;
    }


}
