package com.linkage.repository;

import com.linkage.domain.Role;
import com.linkage.domain.RoleAndUserRelation;
import com.linkage.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RoleAndUserRelationMapper extends BaseMapper<RoleAndUserRelation>{
    /**
     * getroleByUserId
     * @param userid
     * @return role
     */

    Role selectRoleByUserId(Integer userid);

    /**
     * getUserByRoleId
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
    * return Integer
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
     * @param userId
     * @param roleId
     */
    void updateRoleId(Integer roleId, Integer userId);
}
