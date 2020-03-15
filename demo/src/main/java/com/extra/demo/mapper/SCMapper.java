package com.extra.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SCMapper {
    List<String> selectCourseIdListByStudentId(String studentId);
}
