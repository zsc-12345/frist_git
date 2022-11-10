package com.linkage.repository;

import com.linkage.domain.ExamPaper;
import com.linkage.domain.other.KeyValue;
import com.linkage.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.linkage.viewmodel.student.exam.ExamPaperPageVM;
import com.linkage.viewmodel.student.dashboard.PaperFilter;
import com.linkage.viewmodel.student.dashboard.PaperInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ExamPaperMapper extends BaseMapper<ExamPaper> {

    List<ExamPaper> page(ExamPaperPageRequestVM requestVM);

    List<ExamPaper> taskExamPage(ExamPaperPageRequestVM requestVM);

    List<ExamPaper> studentPage(ExamPaperPageVM requestVM);

    List<PaperInfo> indexPaper(PaperFilter paperFilter);

    Integer selectAllCount();

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    int updateTaskPaper(@Param("taskId") Integer taskId,@Param("paperIds") List<Integer> paperIds);

    int clearTaskPaper(@Param("paperIds") List<Integer> paperIds);
}
