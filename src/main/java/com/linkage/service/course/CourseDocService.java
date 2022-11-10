package com.linkage.service.course;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.linkage.domain.course.CourseDoc;
import com.linkage.domain.course.form.CourseDocInfoForm;
import com.linkage.domain.course.form.CourseInfoForm;
import com.linkage.service.BaseService;
import com.linkage.viewmodel.admin.task.TaskPageRequestVM;

public interface CourseDocService extends BaseService<CourseDoc>{
	
    /**
     * getCourseByCourseId
     * @param CourseId
     * @return Course
     */
	CourseDoc getCourseDocById(Integer id);


    /**
     * 新建资源
     * @param Course Course
     * @return
     */
     void insertCourseDoc(CourseDoc courseDoc);

    /**
     * 通过id删除资源
     *
     * @param CourseId
     */
     void deleteById(List<Integer> ids);

    /**
     *更新资源
     * @param Course Course
     * @return
     */
    void updateCourseDoc(CourseDoc courseDoc);

    /**
     * 通过id查找资源
     * @param CourseId
     * @return Course
     */
    CourseDoc selectByCourseId(Integer id);

    /**
     * @param requestVM requestVM
     * @return PageInfo<Course>
     */
     
     PageInfo<CourseDocInfoForm> page(CourseDocInfoForm requestVM);

}
