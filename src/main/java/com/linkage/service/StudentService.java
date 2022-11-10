package com.linkage.service;

import com.github.pagehelper.PageInfo;
import com.linkage.domain.Student;
import com.linkage.viewmodel.student.StudentPageRequestVM;
import com.linkage.viewmodel.student.StudentResponseVM;

import java.util.List;


public interface StudentService extends BaseService<Student>{
    /**
     * getByStudenId
     * @param Id
     * @return student
     */
    Student getByStudentId(Integer Id);

    /**
     * 增加
     * @param student student
     * @return
     */
    void insertByStudent(Student student);

    /**
     * 通过id删除
     *
     * @param Id
     */
    void deleteStudent(Integer Id);

    /**
     * 更新角色
     *
     * @param student student
     * @return
     */
    void updateStudent(Student student);

    /**
     * 通过id查找角色
     * @param Id
     * @return Student
     */
    Student selectByStudentId(Integer Id);

    /**
     * @param requestVM requestVM
     * @return PageInfo<Student>
     */
    PageInfo<Student> studentPage(StudentPageRequestVM requestVM);

}
