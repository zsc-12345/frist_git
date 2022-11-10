package com.linkage.service.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.linkage.domain.course.CourseDoc;
import com.linkage.domain.course.form.CourseDocInfoForm;
import com.linkage.repository.course.CourseDocMapper;
import com.linkage.repository.course.CourseMapper;

@Service
public class CourseDocServiceImpl  implements CourseDocService{
	
	@Autowired
	private CourseDocMapper courseDocMapper;
	
	@Override
	public int insert(CourseDoc record) {
		return courseDocMapper.insert(record);
	}

	@Override
	public int deleteById(Integer id) {
		return courseDocMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public CourseDoc selectById(Integer id) {
		return courseDocMapper.selectByPrimaryKey(id);
	}
	

	@Override
	public void insertCourseDoc(CourseDoc courseDoc) {
		courseDocMapper.insert(courseDoc);
	}
	

	@Override
	public PageInfo<CourseDocInfoForm> page(CourseDocInfoForm requestVM) {
		return null;
	}
	

	@Override
	public int updateById(CourseDoc record) {
		return 0;
	}

	@Override
	public CourseDoc getCourseDocById(Integer id) {
		return null;
	}

	@Override
	public void deleteById(List<Integer> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCourseDoc(CourseDoc courseDoc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CourseDoc selectByCourseId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int insertByFilter(CourseDoc record) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	@Override
	public int updateByIdFilter(CourseDoc record) {
		// TODO Auto-generated method stub
		return 0;
	}

}
