package com.extra.demo.service;

import com.extra.demo.bean.Course;

import java.util.List;

public interface SchoolService {
    List<Course> getCourseInfo(String studentId);
}
