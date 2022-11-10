package com.linkage.service;

import com.linkage.domain.AuthAndRoleRelation;

import java.util.List;

public interface AuthAndRoleRelationService extends BaseService<AuthAndRoleRelation> {

    /**
     * insertAuthRoleRLN
     * @param authAndRoleRelation
     */
    void insertAuthRoleRLN(List<AuthAndRoleRelation> authAndRoleRelation);

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
