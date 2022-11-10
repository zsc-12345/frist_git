package com.linkage.viewmodel.role;

import com.linkage.viewmodel.BaseVM;

public class ChangeAuthsVM extends BaseVM {
    private String roleId;
    private String ids;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
