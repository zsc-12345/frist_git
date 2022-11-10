package com.linkage.controller.dingtalk;

import com.linkage.base.RestResponse;
import com.linkage.controller.admin.UserStudentController;
import com.linkage.domain.plan.CreateLearnPlanVM;
import com.linkage.domain.plan.LearnPlan;
import com.linkage.domain.plan.PlanRelation;
import com.linkage.service.LearnPlanService;
import com.linkage.service.PlanRelationService;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/admin/learnPlan")
public class LearnPlanController {

    private LearnPlanService learnPlanService;
    private PlanRelationService planRelationService;
    private static final Logger logger = LoggerFactory.getLogger(UserStudentController.class);

    @Autowired
    public LearnPlanController(LearnPlanService learnPlanService, PlanRelationService planRelationService) {
        this.learnPlanService = learnPlanService;
        this.planRelationService = planRelationService;
    }

    @RequestMapping("/addLearnPlan")
    public RestResponse addLearnPlan(@RequestBody LearnPlan learnPlan) {
        try {
            if (learnPlanService.getLearnPlanByName(learnPlan.getPlanName()) == null){
                learnPlanService.addLearnPlan(learnPlan);
                logger.info("LearnPlanController.addLearnPlan:学习计划增加成功, name" + learnPlan.getPlanName());
                return RestResponse.ok(learnPlan);
            }
            logger.info("学习计划已存在");
            return RestResponse.fail(500,"学习计划已存在");
        } catch (Exception e) {
            logger.error("学习计划查询失败" + e.getMessage());
            return RestResponse.fail(500, e.getMessage());
        }
    }

    @RequestMapping("/addPlanRelation")
    public RestResponse<PlanRelation> addPlanRelation(@RequestBody CreateLearnPlanVM planVM) {
        try {
            System.out.println(planVM);
            PlanRelation planRelation = new PlanRelation();
            List<String> studentIds = planVM.getStudentIds();
            List<String> teacherIds = planVM.getTeacherIds();
            List<String> adminIds = planVM.getAdminIds();
            LearnPlan learnPlan = learnPlanService.getLearnPlanByName(planVM.getPlanName());
            System.out.println(learnPlan);
            int type = planVM.getType();
            if (type == 1) {
                for (String studentId : studentIds) {
                    planRelation.setLearnPlanId(learnPlan.getPlanId());
                    planRelation.setType(type);
                    planRelation.setOutId(studentId);
                    planRelationService.addPlanRelation(planRelation);
                }
            }
            if (type == 2) {
                for (String teacherId : teacherIds) {
                    planRelation.setLearnPlanId(learnPlan.getPlanId());
                    planRelation.setType(type);
                    planRelation.setOutId(teacherId);
                    planRelationService.addPlanRelation(planRelation);
                }
            }
            if (type == 3) {
                for (String adminId : adminIds) {
                    planRelation.setLearnPlanId(learnPlan.getPlanId());
                    planRelation.setType(type);
                    planRelation.setOutId(adminId);
                    planRelationService.addPlanRelation(planRelation);
                }
            }
            if (type == 4) {
                planRelation.setLearnPlanId(learnPlan.getPlanId());
                planRelation.setType(type);
                planRelation.setOutId(planVM.getSubjectId());
                planRelation.setPlanRelationStartDate(planVM.getPlanRelationStartDate());
                planRelation.setPlanRelationCorn(planVM.getPlanRelationCorn());
                planRelationService.addPlanRelation(planRelation);
            }
            if (type == 5) {
                planRelation.setLearnPlanId(learnPlan.getPlanId());
                planRelation.setType(type);
                planRelation.setOutId(planVM.getExamPaperId());
                planRelation.setPlanRelationStartDate(planVM.getPlanRelationStartDate());
                planRelation.setPlanRelationCorn(planVM.getPlanRelationCorn());
                planRelationService.addPlanRelation(planRelation);
            }
            logger.info("LearnPlanController.addPlanRelation:计划增加成功");
            return RestResponse.ok(planRelation);
        } catch (Exception e) {
            logger.error("计划查询失败" + e.getMessage());
            return RestResponse.fail(2, "计划增加失败");
        }
    }

    @RequestMapping("/planRelationList")
    public RestResponse<List<PlanRelation>> planRelationList(@RequestBody CreateLearnPlanVM planVM){
        LearnPlan learnPlan = learnPlanService.getLearnPlanByName(planVM.getPlanName());
        List<PlanRelation> planRelations = planRelationService.getPlanRelations(learnPlan.getPlanId());
        return RestResponse.ok(planRelations);
    }

    @RequestMapping(value = "/deleteById/{planRelationId}",method = RequestMethod.POST)
    public void deletePlanRelationById(@PathVariable("planRelationId") int planRelationId){
        planRelationService.deleteById(planRelationId);
    }


}
