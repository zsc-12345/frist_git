package com.linkage.viewmodel.student;

import com.linkage.domain.Student;
import com.linkage.utility.DateTimeUtil;
import com.linkage.viewmodel.BaseVM;



public class StudentResponseVM extends BaseVM {
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
    private String modify_time;

    /**
     * 创建时间
     */
    private String create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public static StudentResponseVM from(Student student) {
        StudentResponseVM vm = modelMapper.map(student, StudentResponseVM.class);
        vm.setModify_time(DateTimeUtil.dateFormat(student.getModify_time()));
        vm.setCreate_time(DateTimeUtil.dateFormat(student.getCreate_time()));
        return vm;
    }


    @Override
    public String toString() {
        return "StudentResponseVM{" +
                "id=" + id +
                ", descrption='" + descrption + '\'' +
                ", status='" + status + '\'' +
                ", modify_time=" + modify_time +
                ", create_time=" + create_time +
                '}';
    }

}
