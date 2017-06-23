package com.puputuan.model;


import com.puputuan.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

@Table(name = "pu_fancy_user")
public class FancyUser extends BaseModel {
    @Id
    @Column
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long fancyId;

    @Column
    private Date createTime;

    @Transient
    private Fancy fancy;

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

    public Long getFancyId() {
        return fancyId;
    }

    public void setFancyId(Long fancyId) {
        this.fancyId = fancyId;
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

    public Fancy getFancy() {
        return fancy;
    }

    public void setFancy(Fancy fancy) {
        this.fancy = fancy;
    }
}
