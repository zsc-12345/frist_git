package com.linkage.repository;

import com.linkage.domain.UserStudent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserStudentMapper extends BaseMapper<UserStudent>{
    //新增数据
    int add(UserStudent userStudent);
    //删除数据
    int deleteById(int id);
    //根据id查找
    UserStudent selectById(int id);
    //更新数据
    int updateStudentById(UserStudent userStudent);

}
