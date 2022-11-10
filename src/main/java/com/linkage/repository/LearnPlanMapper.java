package com.linkage.repository;

import com.linkage.domain.plan.LearnPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface LearnPlanMapper {

    void addLearnPlan(LearnPlan learnPlan);

    LearnPlan getLearnPlanByName(@Param("planName")String name);
}
