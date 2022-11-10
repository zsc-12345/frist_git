package com.linkage.domain;

import java.util.Date;
/**
 * 实体类
 */
public class Student {

    private Integer id;

    /**
     * 描述
     */
    private String descrption;

    /**
     * 状态
     * 状态(“0”：删除  ，“1”：存在)
     */
    private Integer status;

    /**
     * 更新时间
     */
    private Date modify_time;

    /**
     * 创建时间
     */
    private Date create_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", descrption='" + descrption + '\'' +
                ", status='" + status + '\'' +
                ", modify_time=" + modify_time +
                ", create_time=" + create_time +
                '}';
    }
}
