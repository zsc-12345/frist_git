package com.linkage.service;

import com.github.pagehelper.PageInfo;
import com.linkage.domain.Practice;
import com.linkage.domain.User;
import com.linkage.viewmodel.admin.practice.PracticePaperEditRequestVM;
import com.linkage.viewmodel.admin.practice.PracticePaperPageRequestVM;
import com.linkage.viewmodel.student.Practice.PracticePageVM;
import com.linkage.viewmodel.student.dashboard.PracticeFilter;
import com.linkage.viewmodel.student.dashboard.PracticeInfo;

import java.util.List;

public interface PracticeService extends BaseService<Practice>{
    PageInfo<Practice> page(PracticePaperPageRequestVM requestVM);

    PageInfo<Practice> taskExamPage(PracticePaperPageRequestVM requestVM);

    PageInfo<Practice> studentPage(PracticePageVM requestVM);

    Practice savePaperFromVM(PracticePaperEditRequestVM practicePaperEditRequestVM, User user);

    PracticePaperEditRequestVM examPaperToVM(Integer id);

    List<PracticeInfo> indexPaper(PracticeFilter practiceFilter);

    Integer selectAllCount();

    List<Integer> selectMothCount();
}
