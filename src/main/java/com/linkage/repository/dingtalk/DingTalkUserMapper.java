package com.linkage.repository.dingtalk;

import com.linkage.domain.dingtalk.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface DingTalkUserMapper {

    List<UserDTO> getAllUsers();

    void updateByPrimaryKey(UserDTO userDTO);

    void insertUser(UserDTO userDTO);

    UserDTO getUserById(@Param("userid") String userid);
}
