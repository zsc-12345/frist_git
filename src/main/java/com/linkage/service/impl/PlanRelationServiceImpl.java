package com.linkage.service.impl;

import com.linkage.domain.plan.PlanRelation;
import com.linkage.repository.PlanRelationMapper;
import com.linkage.service.PlanRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanRelationServiceImpl implements PlanRelationService {

    private PlanRelationMapper planRelationMapper;

    @Autowired
    public PlanRelationServiceImpl(PlanRelationMapper planRelationMapper) {
        this.planRelationMapper = planRelationMapper;
    }

    @Override
    public List<PlanRelation> getPlanRelations(int learnPlanId) {
        return planRelationMapper.getPlanRelations(learnPlanId);
    }

    @Override
    public void addPlanRelation(PlanRelation planRelation) {
        planRelationMapper.addPlanRelation(planRelation);
    }

    @Override
    public void deleteById(int id) {
        planRelationMapper.deleteById(id);
    }
}
