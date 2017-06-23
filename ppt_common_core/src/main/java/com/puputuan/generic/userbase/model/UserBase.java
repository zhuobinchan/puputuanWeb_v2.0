package com.puputuan.generic.userbase.model;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by jian on 2015/6/17.
 */
@Table(name = "geetion_user_base")
public class UserBase implements Serializable {

    @Column
    private Long id;

    @Column
    @NotNull
    private String account;

    @Column
    @NotNull
    private String password;

    @Column
    @NotNull
    private String name;

    @Column
    private String token;

    @Column
    private Integer freeze;

    @Column
    private Date createTime;

    @Column
    private Date loginTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getFreeze() {
        return freeze;
    }

    public void setFreeze(Integer freeze) {
        this.freeze = freeze;
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

    public Date getLoginTime() {
        if (loginTime == null) {
            return null;
        }
        return (Date) loginTime.clone();
    }

    public void setLoginTime(Date loginTime) {
        if (loginTime == null) {
            this.loginTime = null;
        } else {
            this.loginTime = (Date) loginTime.clone();
        }
    }


}