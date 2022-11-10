package com.linkage.domain.dingtalk;

import java.io.Serializable;
import java.util.List;

import com.linkage.domain.plan.LearnPlan;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author openapi@dingtalk
 * @date 2020/2/4
 */
public class UserDTO implements Serializable {
    //用户id
    private String userid;
    //员工姓名
    private String name;
    //员工工号
    private String jobNumber;
    //用户头像地址
    private String avatar;
    //入职日期
    private Long hiredDate;
    //用户在当前开发者企业帐号范围内的唯一标识
    private String unionId;
    //职位
    private String position;
    //手机号码
    private String mobile;
    //分机号
    private String tel;
    //员工邮箱
    private String email;
    //员工企业邮箱
    private String orgEmail;
    //工作地
    private String workPlace;
    //备注
    private String remark;
    //是否激活钉钉
    private Boolean active;
    //是否为管理员
    private Boolean isAdmin;
    //是否为老板
    private Boolean isBoss;
    //是否号码隐藏
    private Boolean isHide;
    //是否为部门主管
    private Boolean isLeader;
    //学习计划
    private List<LearnPlan> plans;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Long hiredDate) {
        this.hiredDate = hiredDate;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getBoss() {
        return isBoss;
    }

    public void setBoss(Boolean boss) {
        isBoss = boss;
    }

    public Boolean getHide() {
        return isHide;
    }

    public void setHide(Boolean hide) {
        isHide = hide;
    }

    public Boolean getLeader() {
        return isLeader;
    }

    public void setLeader(Boolean leader) {
        isLeader = leader;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
