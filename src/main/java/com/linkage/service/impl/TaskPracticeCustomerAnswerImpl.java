//package com.linkage.service.impl;
//
//import com.linkage.domain.*;
//import com.linkage.domain.task.TaskItemAnswerObject;
//import com.linkage.repository.TaskExamCustomerAnswerMapper;
//import com.linkage.service.TaskExamCustomerAnswerService;
//import com.linkage.service.TextContentService;
//import com.linkage.utility.JsonUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class TaskPracticeCustomerAnswerImpl extends BaseServiceImpl<TaskExamCustomerAnswer> implements TaskExamCustomerAnswerService {
//
//    private final TaskExamCustomerAnswerMapper taskExamCustomerAnswerMapper;
//    private final TextContentService textContentService;
//
//    @Autowired
//    public TaskPracticeCustomerAnswerImpl(TaskExamCustomerAnswerMapper taskExamCustomerAnswerMapper, TextContentService textContentService) {
//        super(taskExamCustomerAnswerMapper);
//        this.taskExamCustomerAnswerMapper = taskExamCustomerAnswerMapper;
//        this.textContentService = textContentService;
//    }
//
//    @Override
//    public void insertOrUpdate(Practice practice, PracticePaperAnswer practicePaperAnswer, Date now) {
//        Integer taskId = practice.getTaskPracticeId();
//        Integer userId = practicePaperAnswer.getCreateUser();
//        TaskPracticeCustomerAnswer taskPracticeCustomerAnswer = taskExamCustomerAnswerMapper.getByTUid(taskId, userId);
//        if (null == taskPracticeCustomerAnswer) {
//            taskPracticeCustomerAnswer = new TaskPracticeCustomerAnswer();
//            taskPracticeCustomerAnswer.setCreateTime(now);
//            taskPracticeCustomerAnswer.setCreateUser(userId);
//            taskPracticeCustomerAnswer.setTaskPracticeId(taskId);
//            List<TaskItemAnswerObject> taskItemAnswerObjects = Arrays.asList(new TaskItemAnswerObject(practicePaperAnswer.getTaskPracticeId(), practicePaperAnswer.getId(), practicePaperAnswer.getStatus()));
//            TextContent textContent = textContentService.jsonConvertInsert(taskItemAnswerObjects, now, null);
//            textContentService.insertByFilter(textContent);
//            taskPracticeCustomerAnswer.setTextContentId(textContent.getId());
//            insertByFilter(taskPracticeCustomerAnswer);
//        } else {
//            TextContent textContent = textContentService.selectById(taskPracticeCustomerAnswer.getTextContentId());
//            List<TaskItemAnswerObject> taskItemAnswerObjects = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemAnswerObject.class);
//            taskItemAnswerObjects.add(new TaskItemAnswerObject(practicePaperAnswer.getTaskPracticeId(), practicePaperAnswer.getId(), practicePaperAnswer.getStatus()));
//            textContentService.jsonConvertUpdate(textContent, taskItemAnswerObjects, null);
//            textContentService.updateByIdFilter(textContent);
//        }
//    }
//
//    @Override
//    public TaskExamCustomerAnswer selectByTUid(Integer tid, Integer uid) {
//        return taskExamCustomerAnswerMapper.getByTUid(tid, uid);
//    }
//
//    @Override
//    public List<TaskExamCustomerAnswer> selectByTUid(List<Integer> taskIds, Integer uid) {
//        return taskExamCustomerAnswerMapper.selectByTUid(taskIds, uid);
//    }
//}
