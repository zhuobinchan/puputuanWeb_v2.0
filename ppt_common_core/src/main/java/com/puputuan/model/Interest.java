package com.puputuan.model;


import com.puputuan.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

@Table(name = "pu_interest")
public class Interest extends BaseModel {
    @Id
    @Column
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column
    private String name;

    @Column
    private Long identify;

    @Column
    private Integer type;

    @Column
    private Integer status;

    @Transient
    private Boolean haveSelect = false;

    @Column
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdentify() {
        return identify;
    }

    public void setIdentify(Long identify) {
        this.identify = identify;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getHaveSelect() {
        return haveSelect;
    }

    public void setHaveSelect(Boolean haveSelect) {
        this.haveSelect = haveSelect;
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
}