package com.extra.demo.controller;

import com.extra.demo.bean.Course;
import com.extra.demo.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class SchoolController {

    @Autowired
    SchoolService schoolService;


    //获取选课信息
    @RequestMapping("/getCourseInfo")
    @ResponseBody
    public List<Course> getCourseInfo(@RequestBody Map<String,String> map){
        String studentId = map.get("studentId");
        List<Course> courses = schoolService.getCourseInfo(studentId);
        return courses;
    }


}
