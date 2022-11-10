package com.linkage.repository;

import com.linkage.domain.AuthEntity;
import com.linkage.viewmodel.role.AuthPageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthMapper extends BaseMapper<AuthEntity>{

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
     * @return List<Role>
     */
    List<AuthEntity> authPage(AuthPageRequestVM requestVM);


}
