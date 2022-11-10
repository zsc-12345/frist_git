package com.linkage.service;


import com.github.pagehelper.PageInfo;
import com.linkage.domain.Role;
import com.linkage.domain.User;
import com.linkage.viewmodel.admin.user.UserPageRequestVM;
import com.linkage.viewmodel.role.RolePageRequestVM;

import java.util.List;

public interface RoleService extends BaseService<Role> {
    /**
     * getRoleByRoleId
     * @param roleId
     * @return role
     */
    Role getRoleByRoleId(Integer roleId);


    /**
     * 新建角色
     * @param role role
     * @return
     */
     void insertRole(Role role);

    /**
     * 通过id删除角色
     *
     * @param roleId
     */
     void deleteById(List<Integer> roleId);

    /**
     *更新角色
     * @param role role
     * @return
     */
    void updateRole(Role role);

    /**
     * 通过id查找角色
     * @param roleId
     * @return role
     */
     Role selectByRoleId(Integer roleId);

    /**
     * @param requestVM requestVM
     * @return PageInfo<Role>
     */
    PageInfo<Role> rolePage(RolePageRequestVM requestVM);
}
