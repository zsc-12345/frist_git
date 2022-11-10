package com.linkage.domain;

import java.io.Serializable;
/**
 * 实体类
 */
public class UserStudent implements Serializable {
    private Integer id;
    /**
     * 用户id
     */
    private Integer user_id;
    /**
     * 学生id
     */
    private Integer stu_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getStuId() {
        return stu_id;
    }

    public void setStuId(Integer stu_id) {
        this.stu_id = stu_id;
    }

    @Override
    public String toString() {
        return "UserStudent{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", stu_id=" + stu_id +
                '}';
    }

    public UserStudent() {
    }
}
