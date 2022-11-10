package com.linkage.service;

import com.linkage.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.linkage.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.linkage.viewmodel.student.exam.ExamPaperPageVM;
import com.linkage.domain.ExamPaper;
import com.linkage.domain.User;
import com.linkage.viewmodel.student.dashboard.PaperFilter;
import com.linkage.viewmodel.student.dashboard.PaperInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ExamPaperService extends BaseService<ExamPaper> {

    PageInfo<ExamPaper> page(ExamPaperPageRequestVM requestVM);

    PageInfo<ExamPaper> taskExamPage(ExamPaperPageRequestVM requestVM);

    PageInfo<ExamPaper> studentPage(ExamPaperPageVM requestVM);

    ExamPaper savePaperFromVM(ExamPaperEditRequestVM examPaperEditRequestVM, User user);

    ExamPaperEditRequestVM examPaperToVM(Integer id);

    List<PaperInfo> indexPaper(PaperFilter paperFilter);

    Integer selectAllCount();

    List<Integer> selectMothCount();
}
