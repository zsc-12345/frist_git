package com.linkage.repository;

import com.linkage.domain.Teacher;
import com.linkage.domain.other.KeyValue;
import com.linkage.viewmodel.admin.teacher.TeacherPageRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

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
     * deleteByPrimaryKey
     * @param Id
     * @return
     */
    void deleteByPrimaryKey(List<Integer> Id);

    /**
     * updateTeacher
     *
     * @param teacher teacher
     */
    void updateTeacher(Teacher teacher);

    /**
     * selectByTeacherId
     * @param teacherId
     * @return teacher
     */
    Teacher selectByTeacherId(Integer teacherId);


    /**
     * @param requestVM requestVM
     * @return List<Teacher>
     */
    List<Teacher> teacherPage(TeacherPageRequestVM requestVM);


}
