package com.linkage.service.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linkage.domain.course.Course;
import com.linkage.domain.course.form.CourseInfoForm;
import com.linkage.repository.course.CourseMapper;

@Service
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseMapper courseMapper;
	

	@Override
	public int insert(Course record) {
		courseMapper.insert(record);
		return 0;
	}
	

	@Override
	public int deleteById(Integer id) {
		//TODO 删除课程时：资源视频或文档是否删除，采用逻辑状态删除
		return courseMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public void updateCourse(Course course) {
		courseMapper.updateByPrimaryKey(course);
	}

	@Override
	public Course selectByCourseId(Integer courseId) {
		return courseMapper.selectByPrimaryKey(courseId);
	}


	@Override
	public PageInfo<CourseInfoForm> page(CourseInfoForm requestVM) {
		return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
		courseMapper.page(requestVM)
		);
	}

	@Override
	public int insertByFilter(Course record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Course selectById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByIdFilter(Course record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateById(Course record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Course getCourseByCourseId(Integer courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertCourse(Course course) {
		
	}

	@Override
	public void deleteById(List<Integer> courseId) {
		
	}

}
