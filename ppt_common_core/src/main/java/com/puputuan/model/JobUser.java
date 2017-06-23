package com.puputuan.model;


import com.puputuan.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

@Table(name = "pu_job_user")
public class JobUser extends BaseModel {
    @Id
    @Column
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long jobId;

    @Transient
    private Job job;

    @Column
    private Date createTime;

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

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Date getCreateTime() {
        if (createTime == null) {
            return null;
        }
        return (Date) createTime.clone();
    }

    public void setCreateTime(Date createTime){
        if (createTime == null) {
            this.createTime = null;
        } else {
            this.createTime = (Date) createTime.clone();
        }
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}