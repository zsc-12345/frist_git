package com.linkage.controller.admin;

import com.github.pagehelper.PageInfo;
import com.linkage.base.BaseApiController;
import com.linkage.base.RestResponse;
import com.linkage.domain.*;
import com.linkage.domain.enums.ExamPaperAnswerStatusEnum;
import com.linkage.domain.enums.PracticePaperAnswerStatusEnum;
import com.linkage.event.CalculateExamPaperAnswerCompleteEvent;
import com.linkage.event.CalculatePracticePaperAnswerCompleteEvent;
import com.linkage.event.UserEvent;
import com.linkage.service.PracticePaperAnswerService;
import com.linkage.service.PracticeService;
import com.linkage.service.SubjectService;
import com.linkage.utility.DateTimeUtil;
import com.linkage.utility.ExamUtil;
import com.linkage.utility.PageInfoHelper;
import com.linkage.utility.PracticeUtil;
import com.linkage.viewmodel.admin.practice.PracticePaperEditRequestVM;
import com.linkage.viewmodel.student.Practice.PracticePaperReadVM;
import com.linkage.viewmodel.student.Practice.PracticePaperSubmitVM;
import com.linkage.viewmodel.student.practicepaper.PracticePaperAnswerPageResponseVM;
import com.linkage.viewmodel.student.practicepaper.PracticePaperAnswerPageVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Api(tags = "练习答案Controller")
@RestController("AdminPracticePaperAnswerController")
@RequestMapping(value = "/api/admin/practicePaperAnswer")
public class PracticePaperAnswerController extends BaseApiController {

    private final PracticePaperAnswerService practicePaperAnswerService;
    private final PracticeService practiceService;
    private final SubjectService subjectService;
    private final ApplicationEventPublisher eventPublisher;


    @Autowired
    public PracticePaperAnswerController(PracticePaperAnswerService practicePaperAnswerService, PracticeService practiceService, SubjectService subjectService, ApplicationEventPublisher eventPublisher) {
        this.practicePaperAnswerService = practicePaperAnswerService;
        this.practiceService = practiceService;
        this.subjectService = subjectService;
        this.eventPublisher = eventPublisher;
    }

    @ApiOperation(value="练习页面列表任务")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public RestResponse<PageInfo<PracticePaperAnswerPageResponseVM>> pageList(@RequestBody @Valid PracticePaperAnswerPageVM model) {
        model.setCreateUser(getCurrentUser().getId());
        PageInfo<PracticePaperAnswer> pageInfo = practicePaperAnswerService.studentPage(model);
        PageInfo<PracticePaperAnswerPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            PracticePaperAnswerPageResponseVM vm = modelMapper.map(e, PracticePaperAnswerPageResponseVM.class);
            Subject subject = subjectService.selectById(vm.getRankLevel());
            vm.setDoTime(PracticeUtil.secondToVM(e.getDoTime()));
            vm.setSystemScore(PracticeUtil.scoreToVM(e.getSystemScore()));
            vm.setUserScore(PracticeUtil.scoreToVM(e.getUserScore()));
            vm.setPaperScore(PracticeUtil.scoreToVM(e.getPaperScore()));
            vm.setSubjectName(subject.getName());
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }

    @ApiOperation(value="练习答案提交任务")
    @RequestMapping(value = "/answerSubmit", method = RequestMethod.POST)
    public RestResponse answerSubmit(@RequestBody @Valid PracticePaperSubmitVM practicePaperSubmitVM) {
        User user = getCurrentUser();
        PracticePaperAnswerInfo practicePaperAnswerInfo = practicePaperAnswerService.calculateExamPaperAnswer(practicePaperSubmitVM, user);
        if (null == practicePaperAnswerInfo) {
            return RestResponse.fail(2, "试卷不能重复做");
        }
        PracticePaperAnswer practicePaperAnswer = practicePaperAnswerInfo.getPracticePaperAnswer();
        Integer userScore = practicePaperAnswer.getUserScore();
        String scoreVm = ExamUtil.scoreToVM(userScore);
        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getRealName(), new Date());
        String content = user.getUserName() + " 提交试卷：" + practicePaperAnswerInfo.getPractice().getName()
                + " 得分：" + scoreVm
                + " 耗时：" + ExamUtil.secondToVM(practicePaperAnswer.getDoTime());
        userEventLog.setContent(content);
        eventPublisher.publishEvent(new CalculatePracticePaperAnswerCompleteEvent(practicePaperAnswerInfo));
        eventPublisher.publishEvent(new UserEvent(userEventLog));
        return RestResponse.ok(scoreVm);
    }


    @ApiOperation(value="练习试卷批改编辑任务")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse edit(@RequestBody @Valid PracticePaperSubmitVM practicePaperSubmitVM) {
        boolean notJudge = practicePaperSubmitVM.getAnswerItems().stream().anyMatch(i -> i.getDoRight() == null && i.getScore() == null);
        if (notJudge) {
            return RestResponse.fail(2, "有未批改题目");
        }

        PracticePaperAnswer practicePaperAnswer = practicePaperAnswerService.selectById(practicePaperSubmitVM.getId());
        PracticePaperAnswerStatusEnum practicePaperAnswerStatusEnum = PracticePaperAnswerStatusEnum.fromCode(practicePaperAnswer.getStatus());
        if (practicePaperAnswerStatusEnum == PracticePaperAnswerStatusEnum.Complete) {
            return RestResponse.fail(3, "试卷已完成");
        }
        String score = practicePaperAnswerService.judge(practicePaperSubmitVM);
        User user = getCurrentUser();
        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getRealName(), new Date());
        String content = user.getUserName() + " 批改试卷：" + practicePaperAnswer.getPaperName() + " 得分：" + score;
        userEventLog.setContent(content);
        eventPublisher.publishEvent(new UserEvent(userEventLog));
        return RestResponse.ok(score);
    }

    @ApiOperation(value="练习试卷查看任务")
    @RequestMapping(value = "/read/{id}", method = RequestMethod.POST)
    public RestResponse<PracticePaperReadVM> read(@PathVariable Integer id) {
        PracticePaperAnswer practicePaperAnswer = practicePaperAnswerService.selectById(id);
        PracticePaperReadVM vm = new PracticePaperReadVM();
        PracticePaperEditRequestVM paper = practiceService.examPaperToVM(practicePaperAnswer.getPracticePaperId());
        PracticePaperSubmitVM answer = practicePaperAnswerService.examPaperAnswerToVM(practicePaperAnswer.getId());
        vm.setPaper(paper);
        vm.setAnswer(answer);
        return RestResponse.ok(vm);
    }

}
