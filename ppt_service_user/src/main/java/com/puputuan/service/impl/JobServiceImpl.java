package com.puputuan.service.impl;

import com.puputuan.common.constant.JobTypeAndStatus;
import com.puputuan.dao.JobDAO;
import com.puputuan.dao.JobUserDAO;
import com.puputuan.model.Job;
import com.puputuan.model.JobUser;
import com.puputuan.service.JobService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenzhuobin on 2017/6/2.
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.INTERFACES)
public class JobServiceImpl implements JobService {
    private static Logger logger = Logger.getLogger(JobServiceImpl.class);

    @Resource
    private JobUserDAO jobUserDAO;

    @Resource
    private JobDAO jobDAO;

    @Override
    public void userUpdateJob(String[] jobStrings, Long userId) {
        logger.info("JobServiceImpl userUpdateJob begin...");
        Map<String, Object> param = new HashMap<>();
        if (jobStrings != null && jobStrings.length != 0) {
            //删除之前相关的jobUser
            jobUserDAO.deleteByUserId(userId);

            for (String jobName : jobStrings) {
                param.put("name", jobName);
                Job jobFromDB = jobDAO.selectOne(param);
                JobUser jobUser = new JobUser();
                jobUser.setUserId(userId);
                if (jobFromDB != null) {
                    //step2.1：如果该职业在数据库已存在，使用该职业的id
                    jobUser.setJobId(jobFromDB.getId());
                } else {
                    //step2.2：如果该职业在数据库不存在，则新建一个
                    Job job = new Job();
                    job.setName(jobName);

                    // TODO 以后再 redis 微服务中调用
//                    job.setIdentify(application.getBASE_JOB_IDENTIFY());

                    //用户创建的职业
                    job.setType(JobTypeAndStatus.USER_TYPE);
                    //该职业状态--开放
                    job.setStatus(JobTypeAndStatus.OPEN_STATUS);
                    jobDAO.insertSelective(job);
                    jobUser.setJobId(job.getId());
                }
                try {
                    //step2.3：添加职业信息
                    jobUserDAO.insertSelective(jobUser);
                } catch (DuplicateKeyException e) {
                    if (e.getMessage().contains("uq_job_relationship_user_idx_job_user")) {
                        System.out.println("\n重复添加职业，不做处理");
                    }
                }
            }
        }
    }
}
