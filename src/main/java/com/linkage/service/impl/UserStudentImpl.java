package com.linkage.service.impl;

import com.linkage.domain.UserStudent;
import com.linkage.repository.UserStudentMapper;
import com.linkage.service.UserStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserStudentImpl extends BaseServiceImpl<UserStudent> implements UserStudentService {
    private final UserStudentMapper userStudentMapper;
    @Autowired
    public UserStudentImpl(UserStudentMapper userStudentMapper) {
        super(userStudentMapper);
        this.userStudentMapper = userStudentMapper;
    }



    @Override
    public int add(UserStudent userStudent) {
        return userStudentMapper.add(userStudent);
    }

    @Override
    public int deleteById(int id) {
        return userStudentMapper.deleteById(id);
    }

    @Override
    public UserStudent selectById(int id) {
        return userStudentMapper.selectById(id);
    }

    @Override
    public int updateStudentById(UserStudent userStudent) {
        return userStudentMapper.updateStudentById(userStudent);
    }
}
