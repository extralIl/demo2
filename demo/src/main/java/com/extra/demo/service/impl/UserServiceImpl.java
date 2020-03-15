package com.extra.demo.service.impl;

import com.extra.demo.bean.User;
import com.extra.demo.mapper.UserMapper;
import com.extra.demo.service.UserService;
import com.extra.demo.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    UserMapper userMapper;

    /**
     *
     * @param phoneNumber 手机号
     * @return String
     * 设置验证码返回set
     * 重新设置验证码返回reset
     * 发送后不够1分钟返回unset
     *
     * 往redis中存储了verifyNumber:15512095588 这样的String Key,value是6位随机数字
     */
    @Override
    public String setVerifyCode(String phoneNumber) {
        //使用redis中的string存储
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        //获取6位数字验证码
        String verifyNumber = Util.getVerifyNumber();
        System.out.println(verifyNumber);
        String res = null;
        //如果没有发送过验证码,或验证码已过期
        String storeVerifyNumber = "verifyNumber:"+phoneNumber;

        if(!redisTemplate.hasKey(storeVerifyNumber)){
            //设置过期时间为5分钟
            stringObjectValueOperations.set(storeVerifyNumber,verifyNumber,60*5, TimeUnit.SECONDS);
            res = "set";
        }else {//如果缓存中存在未过期验证码
            //获取还有多少秒过期,设置重复发送时间为1分钟,那么剩余时间少于240秒时可以再次发送验证码
            Long expire = redisTemplate.getExpire(storeVerifyNumber);
            if(expire<240){
                //已经生成1分钟了,可以重新发送验证码了
                stringObjectValueOperations.set(storeVerifyNumber,verifyNumber,60*5, TimeUnit.SECONDS);
                res = "reset";
            }else{
                //生成还没到1分钟,不能重新发送
                res = "unset";
            }

        }

        return res;


    }

    /**
     * 成功保存返回1,失败返回0
     * @param user
     * @return
     */
    @Override
    public String saveUserInfo(User user) {
        String res = null;
        try {
            userMapper.insertSelective(user);
            res = "1";
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                System.out.println("邮箱或手机号重复");
            }
            res = "0";
        }
        return res;


//        String verifyNumber = user.getVerifyNumber();
//        String phoneNumber = user.getPhoneNumber();
//        String res = null;
//        //如果有验证码存在
//        if (redisTemplate.hasKey(phoneNumber)) {
//            String trueVerifyNumber= (String)redisTemplate.opsForValue().get(phoneNumber);
//            if(trueVerifyNumber.equals(user.getVerifyNumber())){
//                userMapper.insertSelective(user);
//                //删除验证码
//                redisTemplate.delete(phoneNumber);
//                //保存用户信息后分给用户一个session
//                redisTemplate.opsForHash().put("session:"+phoneNumber,"id",user.getId());
//                //设置过期时间为30分钟
//                redisTemplate.expire("session:"+phoneNumber,30,TimeUnit.MINUTES);
//                res = "right number";
//            }else {
//                res = "wrong number";
//            }
//        }else {
//            //无验证码存在
//            res = "not exist";
//        }
//
//        return res;

    }









    /**
     *
     * @param emall
     * @return
     * 0:没有
     * 1:有
     */
    @Override
    public String checkEmall(String emall) {
        User userForCheckEmall = new User();
        userForCheckEmall.setEmall(emall);
        List<User> hasEmall = userMapper.select(userForCheckEmall);

        if(hasEmall.size()==0){
            return "0";
        }else{
            return "1";
        }

    }

    @Override
    public String checkPhoneNumber(String phoneNumber) {
        User userForCheckPhoneNumber = new User();
        userForCheckPhoneNumber.setPhoneNumber(phoneNumber);
        List<User> hasPhoneNumber = userMapper.select(userForCheckPhoneNumber);

        if(hasPhoneNumber.size()==0){
            return "0";
        }else{
            return "1";
        }
    }





    @Override
    public String checkVerifyNumber(String phoneNumber,String verifyNumber) {

        String verifyNumberKey =  "verifyNumber:"+phoneNumber;

        String res = null;
        //如果有验证码存在
        if (redisTemplate.hasKey(verifyNumberKey)) {
            String trueVerifyNumber= (String)redisTemplate.opsForValue().get(verifyNumberKey);
            if(trueVerifyNumber.equals(verifyNumber)){
                //删除验证码
                redisTemplate.delete(phoneNumber);
                res = "right number";
            }else {
                res = "wrong number";
            }
        }else {
            //无验证码存在
            res = "not exist";
        }
        return res;
    }




    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        User user = new User();
        user.setPhoneNumber(phoneNumber);
        List<User> users = userMapper.select(user);
        return users.get(0);
    }


}
