package com.linkage.service.course;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.linkage.domain.TaskExam;
import com.linkage.domain.course.Course;
import com.linkage.domain.course.form.CourseInfoForm;
import com.linkage.service.BaseService;
import com.linkage.viewmodel.admin.task.TaskPageRequestVM;

public interface CourseService extends BaseService<Course>{
	
    /**
     * getCourseByCourseId
     * @param CourseId
     * @return Course
     */
    Course getCourseByCourseId(Integer courseId);


    /**
     * 新建课程
     * @param Course Course
     * @return
     */
     void insertCourse(Course course);

    /**
     * 通过id删除课程
     *
     * @param CourseId
     */
     void deleteById(List<Integer> courseId);

    /**
     *更新课程
     * @param Course Course
     * @return
     */
    void updateCourse(Course course);

    /**
     * 通过id查找课程
     * @param CourseId
     * @return Course
     */
     Course selectByCourseId(Integer courseId);

    /**
     * @param requestVM requestVM
     * @return PageInfo<Course>
     */
     
     PageInfo<CourseInfoForm> page(CourseInfoForm requestVM);

}
