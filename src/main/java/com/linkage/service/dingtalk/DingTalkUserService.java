package com.linkage.service.dingtalk;

import com.linkage.domain.dingtalk.UserDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DingTalkUserService {

    List<UserDTO> getAllUser();

    void insertUser(UserDTO userDTO);

    void updateByPrimaryKey(UserDTO userDTO);

    UserDTO getUserById(String userid);
}
