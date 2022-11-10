package com.linkage.service;

import com.linkage.domain.plan.PlanRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlanRelationService {

    List<PlanRelation> getPlanRelations(int learnPlanId);

    void addPlanRelation(PlanRelation planRelation);

    void deleteById(int id);

}
