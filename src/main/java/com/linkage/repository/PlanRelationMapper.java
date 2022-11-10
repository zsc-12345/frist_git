package com.linkage.repository;

import com.linkage.domain.plan.PlanRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlanRelationMapper {

    List<PlanRelation> getPlanRelations(@Param("learnPlanId") int learnPlanId);

    void addPlanRelation(PlanRelation planRelation);

    void deleteById(@Param("planRelationId") int planRelationId);

}
