package com.linkage.repository;

import com.linkage.domain.Question;
import com.linkage.domain.QuestionBank;
import com.linkage.domain.other.KeyValue;
import com.linkage.viewmodel.admin.question.QuestionPageRequestVM;
import com.linkage.viewmodel.admin.questionbank.QuestionBankPageRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface QuestionBankMapper extends BaseMapper<QuestionBank> {

    List<QuestionBank> page(QuestionBankPageRequestVM requestVM);

    List<QuestionBank> selectByIds(@Param("ids") List<Integer> ids);

    Integer selectAllCount();

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
