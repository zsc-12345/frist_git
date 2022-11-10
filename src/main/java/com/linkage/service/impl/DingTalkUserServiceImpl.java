package com.linkage.service.impl;

import com.linkage.domain.dingtalk.UserDTO;
import com.linkage.repository.dingtalk.DingTalkUserMapper;
import com.linkage.service.dingtalk.DingTalkUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DingTalkUserServiceImpl implements DingTalkUserService {
    private final DingTalkUserMapper dingTalkUserMapper;

    @Autowired
    public DingTalkUserServiceImpl(DingTalkUserMapper dingTalkUserMapper){
        this.dingTalkUserMapper = dingTalkUserMapper;
    }

    @Override
    public List<UserDTO> getAllUser() {
        return dingTalkUserMapper.getAllUsers();
    }

    @Override
    public void insertUser(UserDTO userDTO) {
        dingTalkUserMapper.insertUser(userDTO);
    }

    @Override
    public void updateByPrimaryKey(UserDTO userDTO) {
        dingTalkUserMapper.updateByPrimaryKey(userDTO);
    }

    @Override
    public UserDTO getUserById(String userid) {
        return dingTalkUserMapper.getUserById(userid);
    }
}
