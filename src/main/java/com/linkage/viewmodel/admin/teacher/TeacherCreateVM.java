package com.linkage.viewmodel.admin.teacher;



import javax.validation.constraints.NotBlank;


public class TeacherCreateVM {

    private Integer id;

    @NotBlank
    private String teacherName;

    private String password;

    private String age;

    private Integer status;

    private Integer role;

    private Integer teacherlevel;

    private String descrption;

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
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getTeacherlevel() {
        return teacherlevel;
    }

    public void setTeacherlevel(Integer teacherlevel) {
        this.teacherlevel = teacherlevel;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }
}
