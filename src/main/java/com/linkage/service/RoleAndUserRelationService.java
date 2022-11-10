package com.linkage.service;

import com.linkage.domain.Role;
import com.linkage.domain.RoleAndUserRelation;
import com.linkage.domain.User;

import java.util.List;

public interface RoleAndUserRelationService extends BaseService<RoleAndUserRelation> {

    /**
     * getroleByUserId
     *
     * @param userid
     * @return role
     */

     Role selectRoleByUserId(Integer userid);

    /**
     * getUserByRoleId
     *
     * @param roleid
     * @return user
     */

    List<User> selectUserByRoleId(Integer roleid);


    /**
     * addUserByRole
     * @param roleAndUserRelation roleAndUserRelation
     */

    void addUserByRole(RoleAndUserRelation roleAndUserRelation);

    /**
     * getUsersByRole
     * @param roleid
     * return List<User>
     */

    List<Integer> getUsersByRole(Integer roleid);

    /**
     * checkExist
     * @param roleId
     * @param userId
     */
    RoleAndUserRelation checkExist(Integer roleId, Integer userId);

    /**
     * deleteUserId
     * @param userId
     */
    void deleteUserId(Integer userId);

    /**
     * updateRoleId
     *  @param roleId
     *  @param userId
     */
    void updateRoleId(Integer roleId, Integer userId);

}
