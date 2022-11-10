package com.linkage.service;

import com.github.pagehelper.PageInfo;
import com.linkage.domain.Teacher;
import com.linkage.domain.other.KeyValue;
import com.linkage.viewmodel.admin.teacher.TeacherPageRequestVM;

import java.util.List;

public interface TeacherService  extends BaseService<Teacher>{

    /**
     * getTeacherByTeacherId
     *
     * @param teacherId teacherId
     * @return Teacher
     */
    Teacher getTeacherByTeacherId(Integer teacherId);

    /**
     * insertTeacher
     *
     * @param teacher teacher
     */
    void insertTeacher(Teacher teacher);

    /**
     * deleteById
     *
     * @param id
     */
    void deleteById(List<Integer> id);

    /**
     * updateTeacher
     *
     * @param teacher teacher
     */
    void updateTeacher(Teacher teacher);

    /**
     * selectByTeacherId
     * @param TeacherId
     * @return Teacher
     */
    Teacher selectByTeacherId(Integer TeacherId);

    /**
     * @param requestVM requestVM
     * @return PageInfo<Teacher>
     */
    PageInfo<Teacher> teacherPage(TeacherPageRequestVM requestVM);


}
