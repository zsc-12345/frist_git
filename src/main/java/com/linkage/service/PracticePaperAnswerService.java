package com.linkage.service;

import com.github.pagehelper.PageInfo;
import com.linkage.domain.PracticePaperAnswer;
import com.linkage.domain.PracticePaperAnswerInfo;
import com.linkage.domain.User;
import com.linkage.viewmodel.admin.paper.PracticePaperAnswerPageRequestVM;
import com.linkage.viewmodel.student.Practice.PracticePaperSubmitVM;
import com.linkage.viewmodel.student.exam.ExamPaperSubmitVM;
import com.linkage.viewmodel.student.practicepaper.PracticePaperAnswerPageVM;

import java.util.List;

public interface PracticePaperAnswerService extends BaseService<PracticePaperAnswer>{

    /**
     * 学生考试记录分页
     *
     * @param requestVM 过滤条件
     * @return PageInfo<PracticePaperAnswer>
     */
    PageInfo<PracticePaperAnswer> studentPage(PracticePaperAnswerPageVM requestVM);

    /**
     * 计算试卷提交结果(不入库)
     *
     * @param practicePaperSubmitVM
     * @param user
     * @return
     */
    PracticePaperAnswerInfo calculateExamPaperAnswer(PracticePaperSubmitVM practicePaperSubmitVM, User user);


    /**
     * 试卷批改
     * @param practicePaperSubmitVM  practicePaperSubmitVM
     * @return String
     */
    String judge(PracticePaperSubmitVM practicePaperSubmitVM);

    /**
     * 试卷答题信息转成ViewModel 传给前台
     *
     * @param id 试卷id
     * @return ExamPaperSubmitVM
     */
    PracticePaperSubmitVM examPaperAnswerToVM(Integer id);


    Integer selectAllCount();

    List<Integer> selectMothCount();

    PageInfo<PracticePaperAnswer> adminPage(PracticePaperAnswerPageRequestVM requestVM);
}
