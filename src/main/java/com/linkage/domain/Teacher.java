package com.linkage.domain;

import java.io.Serializable;
import java.util.Date;

public class Teacher implements Serializable {

    private Integer id;

    /**
     * 用户名
     */
    private String teacherName;

    private String password;

    private Integer age;

    /**
     * 教师评级
     */
    private Integer teacherLevel;

    /**
     * 1.启用 2禁用
     */
    private Integer status;

    private String descrption;

    private Date createTime;

    private Date modifyTime;

    private Date lastActiveTime;

    /**
     * 是否删除
     */
    private Boolean deleted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName == null ? null : teacherName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getTeacherLevel() {
        return teacherLevel;
    }

    public void setTeacherLevel(Integer teacherLevel) {
        this.teacherLevel = teacherLevel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption == null ? null : descrption.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getLastActiveTime() {
        return lastActiveTime;
    }
    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", teacherName='" + teacherName + '\'' +
                ", password=" + password +
                ", age=" + age +
                ", teacherLevel=" + teacherLevel +
                ", status=" + status +
                ", descrption='" + descrption +
                ", createTime='" + createTime +
                ", modifyTime='" + modifyTime +
                ", lastActiveTime='" + lastActiveTime + '\'' +
                '}';
    }
}
