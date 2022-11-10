package com.linkage.repository;

import com.linkage.domain.Practice;
import com.linkage.domain.other.KeyValue;
import com.linkage.viewmodel.admin.practice.PracticePaperPageRequestVM;
import com.linkage.viewmodel.student.Practice.PracticePageVM;
import com.linkage.viewmodel.student.dashboard.PracticeFilter;
import com.linkage.viewmodel.student.dashboard.PracticeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface PracticeMapper extends BaseMapper<Practice>{

    List<Practice> page(PracticePaperPageRequestVM requestVM);

    List<Practice> taskExamPage(PracticePaperPageRequestVM requestVM);

    List<Practice> studentPage(PracticePageVM requestVM);

    List<PracticeInfo> indexPaper(PracticeFilter practiceFilter);

    Integer selectAllCount();

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    int updateTaskPaper(@Param("taskId") Integer taskId,@Param("paperIds") List<Integer> paperIds);

    int clearTaskPaper(@Param("paperIds") List<Integer> paperIds);

}
