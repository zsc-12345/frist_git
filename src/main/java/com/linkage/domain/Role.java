package com.linkage.domain;

import java.util.Date;

public class Role {
    //角色ID
    private Integer id;
    //角色名称
    private String name;
    //状态(“1”：启用  ，“2”：禁用)
    private Integer status;
    //创建时间
    private Date create_time;
    //更新时间
    private Date modify_time;
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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
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

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", create_time=" + create_time +
                ", modify_time=" + modify_time +
                ", type=" + type +
                ", remark='" + remark + '\'' +
                '}';
    }
}
