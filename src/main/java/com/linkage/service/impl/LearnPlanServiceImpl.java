package com.linkage.service.impl;

import com.linkage.domain.plan.LearnPlan;
import com.linkage.repository.LearnPlanMapper;
import com.linkage.service.LearnPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearnPlanServiceImpl implements LearnPlanService {

    private LearnPlanMapper learnPlanMapper;

    @Autowired
    public LearnPlanServiceImpl(LearnPlanMapper learnPlanMapper) {
        this.learnPlanMapper = learnPlanMapper;
    }

    @Override
    public void addLearnPlan(LearnPlan learnPlan) {
        learnPlanMapper.addLearnPlan(learnPlan);
    }

    @Override
    public LearnPlan getLearnPlanByName(String name) {
        return learnPlanMapper.getLearnPlanByName(name);
    }
}
