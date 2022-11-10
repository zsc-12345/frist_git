package com.linkage.service;

import com.linkage.domain.Practice;
import com.linkage.domain.PracticePaperAnswer;
import com.linkage.domain.TaskExamCustomerAnswer;
import com.linkage.domain.TaskPracticeCustomerAnswer;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
@Mapper
public interface TaskPracticeCustomerAnswerService extends BaseService<TaskPracticeCustomerAnswer>{

    void insertOrUpdate(Practice practice, PracticePaperAnswer practicePaperAnswer, Date now);

    TaskPracticeCustomerAnswer selectByTUid(Integer tid, Integer uid);

    List<TaskPracticeCustomerAnswer> selectByTUid(List<Integer> taskIds, Integer uid);
}
