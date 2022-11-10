package com.linkage.viewmodel.role;

import com.linkage.viewmodel.BaseVM;

public class AuthAndRoleRelationVM extends BaseVM {

    private Integer id;

    private Integer role_id;

    private Integer auth_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public Integer getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(Integer auth_id) {
        this.auth_id = auth_id;
    }
}
