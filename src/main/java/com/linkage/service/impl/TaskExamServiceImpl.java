package com.linkage.service.impl;

import com.linkage.service.enums.ActionEnum;
import com.linkage.utility.DateTimeUtil;
import com.linkage.utility.JsonUtil;
import com.linkage.utility.ModelMapperSingle;
import com.linkage.viewmodel.admin.exam.ExamResponseVM;
import com.linkage.viewmodel.admin.task.TaskPageRequestVM;
import com.linkage.viewmodel.admin.task.TaskRequestVM;
import com.linkage.domain.ExamPaper;
import com.linkage.domain.TaskExam;
import com.linkage.domain.TextContent;
import com.linkage.domain.User;
import com.linkage.domain.task.TaskItemObject;
import com.linkage.repository.ExamPaperMapper;
import com.linkage.repository.TaskExamMapper;
import com.linkage.service.TaskExamService;
import com.linkage.service.TextContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskExamServiceImpl extends BaseServiceImpl<TaskExam> implements TaskExamService {

    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();
    private final TaskExamMapper taskExamMapper;
    private final TextContentService textContentService;
    private final ExamPaperMapper examPaperMapper;

    @Autowired
    public TaskExamServiceImpl(TaskExamMapper taskExamMapper, TextContentService textContentService, ExamPaperMapper examPaperMapper) {
        super(taskExamMapper);
        this.taskExamMapper = taskExamMapper;
        this.textContentService = textContentService;
        this.examPaperMapper = examPaperMapper;
    }

    @Override
    public PageInfo<TaskExam> page(TaskPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                taskExamMapper.page(requestVM)
        );
    }

    @Override
    @Transactional
    public void edit(TaskRequestVM model, User user) {
        ActionEnum actionEnum = (model.getId() == null) ? ActionEnum.ADD : ActionEnum.UPDATE;
        TaskExam taskExam = null;
        if (actionEnum == ActionEnum.ADD) {
            Date now = new Date();
            taskExam = modelMapper.map(model, TaskExam.class);
            taskExam.setCreateUser(user.getId());
            taskExam.setCreateUserName(user.getUserName());
            taskExam.setCreateTime(now);
            taskExam.setDeleted(false);

            //??????????????????
            TextContent textContent = textContentService.jsonConvertInsert(model.getPaperItems(), now, p -> {
                TaskItemObject taskItemObject = new TaskItemObject();
                taskItemObject.setExamPaperId(p.getId());
                taskItemObject.setExamPaperName(p.getName());
                return taskItemObject;
            });
            textContentService.insertByFilter(textContent);
            taskExam.setFrameTextContentId(textContent.getId());
            taskExamMapper.insertSelective(taskExam);

        } else {
            taskExam = taskExamMapper.selectByPrimaryKey(model.getId());
            modelMapper.map(model, taskExam);

            TextContent textContent = textContentService.selectById(taskExam.getFrameTextContentId());
            //???????????????????????????Id????????????????????????
            List<Integer> paperIds = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class)
                    .stream()
                    .map(d -> d.getExamPaperId())
                    .collect(Collectors.toList());
            examPaperMapper.clearTaskPaper(paperIds);

            //??????????????????
            textContentService.jsonConvertUpdate(textContent, model.getPaperItems(), p -> {
                TaskItemObject taskItemObject = new TaskItemObject();
                taskItemObject.setExamPaperId(p.getId());
                taskItemObject.setExamPaperName(p.getName());
                return taskItemObject;
            });
            textContentService.updateByIdFilter(textContent);
            taskExamMapper.updateByPrimaryKeySelective(taskExam);
        }

        //???????????????taskId
        List<Integer> paperIds = model.getPaperItems().stream().map(d -> d.getId()).collect(Collectors.toList());
        examPaperMapper.updateTaskPaper(taskExam.getId(), paperIds);
        model.setId(taskExam.getId());
    }

    @Override
    public TaskRequestVM taskExamToVM(Integer id) {
        TaskExam taskExam = taskExamMapper.selectByPrimaryKey(id);
        TaskRequestVM vm = modelMapper.map(taskExam, TaskRequestVM.class);
        TextContent textContent = textContentService.selectById(taskExam.getFrameTextContentId());
        List<ExamResponseVM> examResponseVMS = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class).stream().map(tk -> {
            ExamPaper examPaper = examPaperMapper.selectByPrimaryKey(tk.getExamPaperId());
            ExamResponseVM examResponseVM = modelMapper.map(examPaper, ExamResponseVM.class);
            examResponseVM.setCreateTime(DateTimeUtil.dateFormat(examPaper.getCreateTime()));
            return examResponseVM;
        }).collect(Collectors.toList());
        vm.setPaperItems(examResponseVMS);
        return vm;
    }

    @Override
    public List<TaskExam> getByGradeLevel(Integer gradeLevel) {
        return taskExamMapper.getByGradeLevel(gradeLevel);
    }
}
