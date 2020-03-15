package com.extra.demo.controller;

import com.extra.demo.bean.User;
import com.extra.demo.service.UserService;
import com.extra.demo.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }


    /**
     * 相当于注册
     * 前端传来一个User对象,包含除id外信息,也包含验证码
     * @return
     */
    @RequestMapping("signUp")
    @ResponseBody
    public String signUp(@RequestBody User user){

        String verifyCodeRes = userService.checkVerifyNumber(user.getPhoneNumber(),user.getVerifyNumber());
        if(verifyCodeRes.equals("right number")){
            String saveRes = userService.saveUserInfo(user);
            if(saveRes.equals("1")){
                String token = JWTUtil.createToken(user);
                return token;
            }else{
                return "插入过程发生异常";
            }
        }else if(verifyCodeRes.equals("wrong number")){
            return "验证码错误";
        }else {
            return "验证码超时";
        }


        /*
         * 有验证码且正确:right number
         * 有验证码不正确:wrong number
         * 无验证码存在:not exist
         * */

    }

    @RequestMapping("checkEmall")
    @ResponseBody
    public String checkEmall(@RequestBody Map<String,String> map){

        String emall = map.get("emall");
        return  userService.checkEmall(emall).equals("0")?"该邮箱不存在":"该邮箱已存在";
    }

    @RequestMapping("checkPhoneNumber")
    @ResponseBody
    public String checkPhoneNumber(@RequestBody Map<String,String> map){

        String phoneNumber = map.get("phoneNumber");
        return  userService.checkPhoneNumber(phoneNumber).equals("0")?"该手机号不存在":"该手机号已存在";
    }







    //直接拆成判断手机号,和判断邮箱两个
    //判断手机号,邮箱是否重复,然后才能进行发送手机验证码
//    @RequestMapping("checkEmallAndPhone")
//    @ResponseBody
//    public String checkEmallAndPhone(@RequestBody Map<String,String> map){
//
//        String emall = map.get("emall");
//        String phoneNumber = map.get("phoneNumber");
//
//        String emallRes = userService.checkEmall(emall);
//        String phoneNumberRes = userService.checkPhoneNumber(phoneNumber);
//        String res="";
//
//        if(emallRes.equals("1")){
//            res += "邮箱已存在";
//
//        }
//        if(phoneNumberRes.equals("1")){
//            res += "手机号已存在";
//        }
//        if(emallRes.equals("1")&&phoneNumberRes.equals("1")){
//            res = "无重复";
//        }
//
//
//        /*
//         * 有验证码且正确:right number
//         * 有验证码不正确:wrong number
//         * 无验证码存在:not exist
//         * */
//        if(res.equals("right number")){
//            return "成功";
//        }else if(res.equals("wrong number")){
//            return "验证码错误";
//        }else {
//            return "验证码超时";
//        }
//    }





//    设置过期时间,前端控制多少秒才能发一次
    /**发送验证码
     * 前端把phoneNumber传过来
     * json格式的,用map接收
     * @param  map
     * @return
     */
    //统一在raw中传参,@RequestParam接收不到,其只能接收到form-data型的,考虑用map接受单一参数
    @RequestMapping("setVerifyCode")
    @ResponseBody
    public String setVerifyCode(@RequestBody Map<String,String> map){
        String res = userService.setVerifyCode(map.get("phoneNumber"));
        /* 设置验证码返回set
          重新设置验证码返回reset
          发送后不够1分钟返回unset*/
        return res;
    }



    //map 传来phoneNumber和验证码
    //短信验证码登录,实验一下redis实现共享session
    @RequestMapping("login")
    @ResponseBody
    public String login(@RequestBody Map<String,String> map){
        String phoneNumber = map.get("phoneNumber");
        String verifyNumber = map.get("verifyNumber");

        String res = null;
        //@TODO判断有手机号X其实不用判断,前端调用checkphone方法判断有手机号才能登陆->检查验证码->调用生成token方法->加密返回token

        if(userService.checkPhoneNumber(phoneNumber).equals("1")){
            //有该手机号
            String verifyNumberRes = userService.checkVerifyNumber(phoneNumber,verifyNumber);
            if(verifyNumberRes.equals("right number")){
                //验证码正确
                User user = userService.getUserByPhoneNumber(phoneNumber);
                String token = JWTUtil.createToken(user);
                res = token;
            }else{
                //少写一些情况
                res = "验证码错误";
            }
        }else {
            res = "无手机号";
        }


        return res;
    }


}
