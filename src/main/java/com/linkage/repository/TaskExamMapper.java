package com.linkage.repository;

import com.linkage.domain.TaskExam;
import com.linkage.viewmodel.admin.task.TaskPageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskExamMapper extends BaseMapper<TaskExam> {

    List<TaskExam> page(TaskPageRequestVM requestVM);

    List<TaskExam> getByGradeLevel(Integer gradeLevel);
}
