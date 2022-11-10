package com.linkage.repository;

import com.linkage.domain.PracticePaperQuestionCustomerAnswer;
import com.linkage.domain.other.ExamPaperAnswerUpdate;
import com.linkage.domain.other.KeyValue;
import com.linkage.viewmodel.student.question.answer.QuestionPagePracticeStudentRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
@Mapper
public interface PracticePaperQuestionCustomerAnswerMapper extends BaseMapper<PracticePaperQuestionCustomerAnswer>{

    List<PracticePaperQuestionCustomerAnswer> selectListByPaperAnswerId(Integer id);

    List<PracticePaperQuestionCustomerAnswer> studentPage(QuestionPagePracticeStudentRequestVM requestVM);

    int insertList(List<PracticePaperQuestionCustomerAnswer> list);

    Integer selectAllCount();

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    int updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates);
}
