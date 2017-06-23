package com.puputuan.generic.permission.model;


import com.puputuan.model.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "geetion_permission")
public class Permission extends BaseModel {
    @Id
    @Column
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column
    private String permission;

    @Column
    private String name;

    @Column
    private Date createTime;

    @Column
    private String extra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        if (createTime == null){
            return null;
        }
        return (Date)createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        if (createTime == null){
            this.createTime = null;
        } else {
            this.createTime = (Date)createTime.clone();
        }
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}