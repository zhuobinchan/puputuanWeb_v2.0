package com.puputuan.model;


import com.puputuan.generic.permission.model.Role;
import com.puputuan.generic.userbase.model.UserBase;
import com.puputuan.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "pu_admin")
public class Admin extends BaseModel {
    @Id
    @Column
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column
    private Long userId;

    @Column
    private String nickName;

    @Column
    private Long headId;

    @Column
    private Date createTime;

    @Column
    private Integer type;

    @Transient
    private UserBase userBase;

    @Transient
    private List<Role> roleList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Long getHeadId() {
        return headId;
    }

    public void setHeadId(Long headId) {
        this.headId = headId;
    }

    public Date getCreateTime() {
        if (createTime == null) {
            return null;
        }
        return (Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        if (createTime == null) {
            this.createTime = null;
        } else {
            this.createTime = (Date) createTime.clone();
        }
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public UserBase getUserBase() {
        return userBase;
    }

    public void setUserBase(UserBase userBase) {
        this.userBase = userBase;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}