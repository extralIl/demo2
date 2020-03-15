package com.extra.demo.mapper;

import com.extra.demo.bean.Course;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper {
    Course selectCourseListByCourseIdList(String courseId);
}
