package com.linkage.repository;

import com.linkage.domain.PracticePaperAnswer;
import com.linkage.domain.other.KeyValue;
import com.linkage.viewmodel.admin.paper.PracticePaperAnswerPageRequestVM;
import com.linkage.viewmodel.student.practicepaper.PracticePaperAnswerPageVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
@Mapper
public interface PracticePaperAnswerMapper extends BaseMapper<PracticePaperAnswer>{

    List<PracticePaperAnswer> studentPage(PracticePaperAnswerPageVM requestVM);

    Integer selectAllCount();

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    PracticePaperAnswer getByPidUid(@Param("pid") Integer paperId, @Param("uid") Integer uid);

    List<PracticePaperAnswer> adminPage(PracticePaperAnswerPageRequestVM requestVM);
}
