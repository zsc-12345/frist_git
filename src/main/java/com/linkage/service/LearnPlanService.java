package com.linkage.service;

import com.linkage.domain.plan.LearnPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LearnPlanService {

    void addLearnPlan(LearnPlan learnPlan);

    LearnPlan getLearnPlanByName(String name);
}
