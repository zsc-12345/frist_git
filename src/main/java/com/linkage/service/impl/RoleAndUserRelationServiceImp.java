package com.linkage.service.impl;

import com.linkage.domain.Role;
import com.linkage.domain.RoleAndUserRelation;
import com.linkage.domain.User;
import com.linkage.repository.RoleAndUserRelationMapper;
import com.linkage.service.RoleAndUserRelationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleAndUserRelationServiceImp extends BaseServiceImpl<RoleAndUserRelation> implements RoleAndUserRelationService {

    private final RoleAndUserRelationMapper roleAndUserRelationMapper;

    public RoleAndUserRelationServiceImp(RoleAndUserRelationMapper roleAndUserRelationMapper) {
        super(roleAndUserRelationMapper);
        this.roleAndUserRelationMapper = roleAndUserRelationMapper;
    }




    @Override
    public Role selectRoleByUserId(Integer userid) {
        return roleAndUserRelationMapper.selectRoleByUserId(userid);
    }

    @Override
    public List<User> selectUserByRoleId(Integer roleid) {
        return roleAndUserRelationMapper.selectUserByRoleId(roleid);
    }

    @Override
    public void addUserByRole(RoleAndUserRelation roleAndUserRelation) {
        roleAndUserRelationMapper.addUserByRole(roleAndUserRelation);
    }

    @Override
    public List<Integer> getUsersByRole(Integer roleid) {
        return roleAndUserRelationMapper.getUsersByRole(roleid);
    }

    @Override
    public RoleAndUserRelation checkExist(Integer roleId, Integer userId) {
        return roleAndUserRelationMapper.checkExist(roleId, userId);
    }

    @Override
    public void deleteUserId(Integer userId) {
        roleAndUserRelationMapper.deleteUserId(userId);
    }

    @Override
    public void updateRoleId(Integer roleId, Integer userId) {
        roleAndUserRelationMapper.updateRoleId(roleId, userId);
    }
}
