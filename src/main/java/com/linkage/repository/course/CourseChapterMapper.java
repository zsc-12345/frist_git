package com.linkage.repository.course;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.linkage.domain.course.CourseChapter;

@Repository
@Mapper
public interface CourseChapterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseChapter record);

    int insertSelective(CourseChapter record);

    CourseChapter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseChapter record);

    int updateByPrimaryKey(CourseChapter record);
}