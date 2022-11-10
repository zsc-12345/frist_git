package com.linkage.service.impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linkage.domain.Teacher;
import com.linkage.repository.TeacherMapper;
import com.linkage.service.TeacherService;
import com.linkage.viewmodel.admin.teacher.TeacherPageRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public  class TeacherServiceImpl extends BaseServiceImpl<Teacher> implements TeacherService{

    private final TeacherMapper teacherMapper;

    @Autowired
    public TeacherServiceImpl(TeacherMapper teacherMapper) {
        super(teacherMapper);
        this.teacherMapper = teacherMapper;
    }

    @Override
    public Teacher getTeacherByTeacherId(Integer id) {
        return teacherMapper.getTeacherByTeacherId(id);
    }

    @Override
    public void insertTeacher(Teacher teacher) {
        teacherMapper.insertTeacher(teacher);
    }

    @Override
    public void deleteById(List<Integer> Id) {
        teacherMapper.deleteByPrimaryKey(Id);
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        teacherMapper.updateTeacher(teacher);
    }

    @Override
    public Teacher selectByTeacherId(Integer teacherId) {
        return teacherMapper.selectByTeacherId(teacherId);
    }

    @Override
    public PageInfo<Teacher> teacherPage(TeacherPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                teacherMapper.teacherPage(requestVM)
        );
    }

}
