package com.linkage.service;

import com.github.pagehelper.PageInfo;
import com.linkage.domain.AuthEntity;
import com.linkage.domain.Role;
import com.linkage.viewmodel.role.AuthPageRequestVM;

import java.util.List;

public interface AuthService extends BaseService<AuthEntity>{
    /**
     *
     * @param auth
     */
    void createAuth(AuthEntity auth);

    /**
     *
     * @param id
     */
    void deldteAuth(Integer id);

    /**
     *
     * @param auth
     */
    void updateAuth(AuthEntity auth);

    /**
     *
     * @param id
     * @return
     */
    AuthEntity SelectAuth(Integer id);

    /**
     * selectAuthById
     * @param id
     * @return
     */
    AuthEntity selectAuthById(Integer id);


    /**
     * @param requestVM requestVM
     * @return PageInfo<AuthEntity>
     */
    PageInfo<AuthEntity> authPage(AuthPageRequestVM requestVM);




}
