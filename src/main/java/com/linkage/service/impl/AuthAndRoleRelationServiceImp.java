package com.linkage.service.impl;

import com.linkage.domain.AuthAndRoleRelation;
import com.linkage.repository.AuthAndRoleRelationMapper;
import com.linkage.service.AuthAndRoleRelationService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuthAndRoleRelationServiceImp extends BaseServiceImpl<AuthAndRoleRelation> implements AuthAndRoleRelationService {

    private final AuthAndRoleRelationMapper authAndRoleRelationMapper;

    public AuthAndRoleRelationServiceImp(AuthAndRoleRelationMapper authAndRoleRelationMapper) {
        super(authAndRoleRelationMapper);
        this.authAndRoleRelationMapper = authAndRoleRelationMapper;
    }

    @Override
    public void insertAuthRoleRLN(List<AuthAndRoleRelation> authAndRoleRelation) {
        for(int i = 0; i < authAndRoleRelation.size(); i++){
            AuthAndRoleRelation authrr = authAndRoleRelation.get(i);
            authAndRoleRelationMapper.insertAuthRoleRLN(authrr);
        }
    }

    @Override
    public void deleteAuthRoleRLN(Integer roleId) {
            authAndRoleRelationMapper.deleteAuthRoleRLN(roleId);
    }

    @Override
    public List<AuthAndRoleRelation> selectAuthRoleRLN(Integer roleId) {
        List<AuthAndRoleRelation> AuthAndRoleRelationList = authAndRoleRelationMapper.selectAuthRoleRLN(roleId);
        return AuthAndRoleRelationList;
    }

    @Override
    public List<String> getAuthNameList(Integer roleId) {
        List<String> AuthNameList = authAndRoleRelationMapper.getAuthNameList(roleId);
        return AuthNameList;
    }
}
