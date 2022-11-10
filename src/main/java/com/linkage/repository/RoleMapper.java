package com.linkage.repository;

import com.linkage.domain.Role;
import com.linkage.domain.User;
import com.linkage.viewmodel.admin.user.UserPageRequestVM;
import com.linkage.viewmodel.role.RolePageRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface RoleMapper extends BaseMapper<Role>{

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
     * @param roleId
     * @return
     */
    void deleteByPrimaryKey(List<Integer> roleId);

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
     * @return List<Role>
     */
    List<Role> rolePage(RolePageRequestVM requestVM);
}
