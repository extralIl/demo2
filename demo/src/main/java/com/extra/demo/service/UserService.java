package com.extra.demo.service;

import com.extra.demo.bean.User;
import org.springframework.stereotype.Service;


public interface UserService {

    String setVerifyCode(String phoneNumber);

    String saveUserInfo(User user);

    String checkEmall(String emall);

    String checkPhoneNumber(String phoneNumber);

    String checkVerifyNumber(String phoneNumber,String verifyNumber);


    User getUserByPhoneNumber(String phoneNumber);
}
