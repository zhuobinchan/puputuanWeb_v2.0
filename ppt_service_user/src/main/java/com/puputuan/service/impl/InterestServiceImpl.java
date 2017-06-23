package com.puputuan.service.impl;

import com.puputuan.common.constant.InterestTypeAndStatus;
import com.puputuan.dao.InterestDAO;
import com.puputuan.dao.InterestUserDAO;
import com.puputuan.model.Interest;
import com.puputuan.model.InterestUser;
import com.puputuan.service.InterestService;
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
public class InterestServiceImpl implements InterestService {
    private static Logger logger = Logger.getLogger(InterestServiceImpl.class);

    @Resource
    private InterestUserDAO interestUserDAO;

    @Resource
    private InterestDAO interestDAO;


    @Override
    public void userUpdateInterest(String[] interestStrings, Long userId) {
        logger.info("InterestServiceImpl userUpdateInterest begin...");
        Map<String, Object> param = new HashMap();
        if (interestStrings != null && interestStrings.length != 0) {
            //删除之前相关的interestUser
            interestUserDAO.deleteByUserId(userId);

            for (String interestName : interestStrings) {
                param.put("name", interestName);
                Interest interestFromDB = interestDAO.selectOne(param);
                InterestUser interestUser = new InterestUser();
                interestUser.setUserId(userId);
                if (interestFromDB != null) {
                    //step3.1：如果该兴趣在数据库已存在，使用该兴趣的id
                    interestUser.setInterestId(interestFromDB.getId());
                } else {
                    //step3.2：如果该兴趣在数据库不存在，则新建一个
                    Interest interest = new Interest();
                    interest.setName(interestName);

                    // TODO 以后再 redis 微服务中调用
//                    interest.setIdentify(application.getBASE_INTERSET_IDENTIFY());


                    //用户创建的兴趣
                    interest.setType(InterestTypeAndStatus.USER_TYPE);
                    //该兴趣状态--开放
                    interest.setStatus(InterestTypeAndStatus.OPEN_STATUS);
                    interestDAO.insertSelective(interest);
                    interestUser.setInterestId(interest.getId());
                }
                try {
                    //step3.3：添加兴趣信息
                    interestUserDAO.insertSelective(interestUser);
                } catch (DuplicateKeyException e) {
                    if (e.getMessage().contains("uq_interset_user_idx_interset_id_user_id")) {
                        System.out.println("\n重复添加兴趣，不做处理");
                    }
                }
            }
        }
    }
}
