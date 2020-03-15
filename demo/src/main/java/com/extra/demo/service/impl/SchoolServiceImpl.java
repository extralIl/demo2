package com.extra.demo.service.impl;

import com.extra.demo.bean.Course;
import com.extra.demo.mapper.CourseMapper;
import com.extra.demo.mapper.SCMapper;
import com.extra.demo.mapper.StudentMapper;
import com.extra.demo.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    CourseMapper courseMapper;
    @Autowired
    SCMapper scMapper;
    @Autowired
    StudentMapper studentMapper;



    @Override
    public List<Course> getCourseInfo(String studentId) {
        List<String> courseIdList = scMapper.selectCourseIdListByStudentId(studentId);
        List<Course> courses = new ArrayList<>();
        for (String courseId : courseIdList) {
            Course course= courseMapper.selectCourseListByCourseIdList(courseId);
            courses.add(course);
        }
        return courses;
    }
}
