package com.linkage.service;

import com.linkage.viewmodel.admin.task.TaskPageRequestVM;
import com.linkage.viewmodel.admin.task.TaskRequestVM;
import com.linkage.domain.TaskExam;
import com.linkage.domain.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TaskExamService extends BaseService<TaskExam> {

    PageInfo<TaskExam> page(TaskPageRequestVM requestVM);

    void edit(TaskRequestVM model, User user);

    TaskRequestVM taskExamToVM(Integer id);

    List<TaskExam> getByGradeLevel(Integer gradeLevel);
}
