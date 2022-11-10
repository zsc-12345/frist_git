package com.linkage.repository;

import com.linkage.domain.AuthAndRoleRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface AuthAndRoleRelationMapper extends BaseMapper<AuthAndRoleRelation>{

    /**
     insertAuthRoleRLN     * @param authAndRoleRelation
     */
    void insertAuthRoleRLN(AuthAndRoleRelation authAndRoleRelation);

    /**
     * deleteAuthRoleRLN
     * @param roleId
     */
    void deleteAuthRoleRLN(Integer roleId);

    /**
     * selectAuthRoleRLN
     * @param roleId
     * @return
     */

    List<AuthAndRoleRelation> selectAuthRoleRLN(Integer roleId);

    /**
     *
     * @param roleId
     * @return
     */
    List<String> getAuthNameList(Integer roleId);
}
