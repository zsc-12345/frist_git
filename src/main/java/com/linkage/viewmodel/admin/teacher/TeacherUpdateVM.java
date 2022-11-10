package com.linkage.viewmodel.admin.teacher;



import javax.validation.constraints.NotBlank;


public class TeacherUpdateVM {

    @NotBlank
    private Integer id;

    private String teacherName;

    @NotBlank

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @NotBlank

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

}
