package com.linkage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linkage.domain.Role;
import com.linkage.repository.RoleMapper;
import com.linkage.service.RoleService;
import com.linkage.viewmodel.role.RolePageRequestVM;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImp extends BaseServiceImpl<Role> implements RoleService {

    private final RoleMapper roleMapper;

    public RoleServiceImp(RoleMapper roleMapper) {
        super(roleMapper);
        this.roleMapper = roleMapper;
    }


    @Override
    public Role getRoleByRoleId(Integer roleId) {
        return roleMapper.getRoleByRoleId(roleId);
    }

    @Override
    public void insertRole(Role role) {
        roleMapper.insertRole(role);

    }

    @Override
    public void deleteById(List<Integer> roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
    }


    @Override
    public void updateRole(Role role) {
        roleMapper.updateRole(role);

    }

    @Override
    public Role selectByRoleId(Integer roleId) {
        return roleMapper.selectByRoleId(roleId);
    }

    @Override
    public PageInfo<Role> rolePage(RolePageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                roleMapper.rolePage(requestVM)
        );
    }
}
