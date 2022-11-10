package com.linkage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linkage.domain.AuthEntity;
import com.linkage.repository.AuthMapper;
import com.linkage.service.AuthService;
import com.linkage.viewmodel.role.AuthPageRequestVM;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImp extends BaseServiceImpl<AuthEntity> implements AuthService {
        private final AuthMapper authMapper;

        public AuthServiceImp(AuthMapper authMapper) {
                super(authMapper);
                this.authMapper = authMapper;
        }

        @Override
        public void createAuth(AuthEntity auth) {
                authMapper.createAuth(auth);

        }

        @Override
        public void deldteAuth(Integer id) {
                authMapper.deldteAuth(id);

        }

        @Override
        public void updateAuth(AuthEntity auth) {
                authMapper.updateAuth(auth);

        }

        @Override
        public AuthEntity SelectAuth(Integer id) {
                return authMapper.SelectAuth(id);
        }

        @Override
        public AuthEntity selectAuthById(Integer id) {
                return authMapper.selectAuthById(id);
        }

        @Override
        public PageInfo<AuthEntity> authPage(AuthPageRequestVM requestVM) {
                return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                        authMapper.authPage(requestVM)
                );
        }

}
