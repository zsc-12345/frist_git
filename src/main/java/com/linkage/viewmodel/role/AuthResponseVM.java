package com.linkage.viewmodel.role;

import com.linkage.domain.AuthEntity;
import com.linkage.utility.DateTimeUtil;
import com.linkage.viewmodel.BaseVM;

public class AuthResponseVM extends BaseVM {
    private Integer id;

    private Integer data_level;

    private String name;

    private String url;

    private Integer status;

    private String create_time;

    private String modify_time;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getData_level() {
        return data_level;
    }

    public void setData_level(Integer data_level) {
        this.data_level = data_level;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthResponseVM{" +
                "id=" + id +
                ", data_level=" + data_level +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", create_time='" + create_time + '\'' +
                ", modify_time='" + modify_time + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public static AuthResponseVM from(AuthEntity auth) {
        AuthResponseVM vm = modelMapper.map(auth, AuthResponseVM.class);
        vm.setModify_time(DateTimeUtil.dateFormat(auth.getModify_time()));
        vm.setCreate_time(DateTimeUtil.dateFormat(auth.getCreate_time()));
        return vm;
    }
}
