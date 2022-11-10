package com.linkage.viewmodel.admin.teacher;

import com.linkage.domain.Teacher;
import com.linkage.utility.DateTimeUtil;
import com.linkage.viewmodel.BaseVM;


public class TeacherResponseVM extends BaseVM {

    private Integer id;

    private String teacherName;
    private String password;
    private Integer age;

    private String lastActiveTime;

    private String createTime;

    private String modifyTime;

    private Integer status;

    private Integer teacherLevel;

    private String descrption;


    public static TeacherResponseVM from(Teacher teacher) {
        TeacherResponseVM vm = modelMapper.map(teacher, TeacherResponseVM.class);
        vm.setLastActiveTime(DateTimeUtil.dateFormat(teacher.getLastActiveTime()));
        vm.setCreateTime(DateTimeUtil.dateFormat(teacher.getCreateTime()));
        vm.setModifyTime(DateTimeUtil.dateFormat(teacher.getModifyTime()));
        return vm;
    }

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
        this.teacherName = teacherName;
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

    public String getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(String lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTeacherLevel() {
        return teacherLevel;
    }

    public void setTeacherLevel(Integer teacherLevel) {
        this.teacherLevel = teacherLevel;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
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
