package com.linkage.repository.course;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.linkage.domain.TaskExam;
import com.linkage.domain.course.Course;
import com.linkage.domain.course.form.CourseInfoForm;
import com.linkage.repository.BaseMapper;
import com.linkage.viewmodel.admin.task.TaskPageRequestVM;

@Repository
@Mapper
public interface CourseMapper  extends BaseMapper<Course>{
    int deleteByPrimaryKey(Integer id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
    
    List<CourseInfoForm> page(CourseInfoForm requestVM);
}