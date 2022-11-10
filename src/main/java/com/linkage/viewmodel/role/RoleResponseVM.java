package com.linkage.viewmodel.role;

import com.linkage.domain.Role;
import com.linkage.utility.DateTimeUtil;
import com.linkage.viewmodel.BaseVM;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RoleResponseVM extends BaseVM {
    //角色ID
    private Integer id;
    //角色名称
    private String name;
    //状态(“0”：删除  ，“1”：存在)
    private Integer status;
    //创建时间
    private String create_time;
    //更新时间
    private String modify_time;
    //角色类别(“0”：普通用户  ，“1”：管理员)
    private Integer type;
    //备注
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static RoleResponseVM from(Role role) {
        RoleResponseVM vm = modelMapper.map(role, RoleResponseVM.class);
        vm.setModify_time(DateTimeUtil.dateFormat(role.getModify_time()));
        vm.setCreate_time(DateTimeUtil.dateFormat(role.getCreate_time()));
          return vm;
    }

    @Override
    public String toString() {
        return "RoleResponseVM{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", create_time=" + create_time +
                ", modify_time=" + modify_time +
                ", type=" + type +
                ", remark='" + remark + '\'' +
                '}';
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
}
