package com.linkage.repository.course;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.linkage.domain.course.CourseDoc;

@Repository
@Mapper
public interface CourseDocMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseDoc record);

    int insertSelective(CourseDoc record);

    CourseDoc selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseDoc record);

    int updateByPrimaryKey(CourseDoc record);
}