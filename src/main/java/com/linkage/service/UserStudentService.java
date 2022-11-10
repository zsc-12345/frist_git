package com.linkage.service;

import com.linkage.domain.UserStudent;

import java.util.List;

public interface UserStudentService extends BaseService<UserStudent>{
    //新增数据
    int add(UserStudent userStudent);
    //删除数据
    int deleteById(int id);
    //根据id查找
    UserStudent selectById(int id);
    //更新数据
    int updateStudentById(UserStudent userStudent);
}
