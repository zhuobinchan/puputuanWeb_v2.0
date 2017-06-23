package com.puputuan.model;


import com.puputuan.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by guodikai on 2016/10/25.
 */
@Table(name = "pu_user_super_like_config")
public class UserSuperLikeConfig extends BaseModel {
    @Id
    @Column
    @GeneratedValue(generator = "JDBC")
    private Long id;
    @Column
    private Long userId;
    @Column
    private Integer times;
    @Column
    private Date createTime;
    @Transient
    private User user;

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

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
