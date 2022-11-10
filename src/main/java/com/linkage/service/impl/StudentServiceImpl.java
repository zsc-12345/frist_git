package com.linkage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linkage.domain.Student;
import com.linkage.repository.StudentMapper;
import com.linkage.service.StudentService;
import com.linkage.viewmodel.student.StudentPageRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService {

    private final StudentMapper StudentMapper;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper) {
        super(studentMapper);
        this.StudentMapper = studentMapper;
    }


    @Override
    public Student getByStudentId(Integer Id) {
        return StudentMapper.getByStudentId(Id);
    }

    @Override
    public void insertByStudent(Student student) {
         StudentMapper.insertByStudent(student);
    }


    @Override
    public void deleteStudent(Integer Id) {
        StudentMapper.deleteStudent(Id);
    }

    @Override
    public void updateStudent(Student student) {
        StudentMapper.updateStudent(student);
    }

    @Override
    public Student selectByStudentId(Integer Id) {
        return StudentMapper.selectByStudentId(Id);
    }

    @Override
    public PageInfo<Student> studentPage(StudentPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                StudentMapper.studentPage(requestVM)
        );
    }

}
