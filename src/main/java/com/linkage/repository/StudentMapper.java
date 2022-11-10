package com.linkage.repository;

import com.linkage.domain.Student;
import com.linkage.viewmodel.student.StudentPageRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface StudentMapper extends BaseMapper<Student>{
    /**
     * getByStudentId
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
     *更新角色
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
     * @return List<Student>
     */
    List<Student> studentPage(StudentPageRequestVM requestVM);
}
