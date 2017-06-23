package com.puputuan.service.impl;

import com.puputuan.common.constant.FancyTypeAndStatus;
import com.puputuan.dao.FancyDAO;
import com.puputuan.dao.FancyUserDAO;
import com.puputuan.model.Fancy;
import com.puputuan.model.FancyUser;
import com.puputuan.service.FancyService;
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
public class FancyServiceImpl implements FancyService {
    private static Logger logger = Logger.getLogger(FancyServiceImpl.class);

    @Resource
    private FancyUserDAO fancyUserDAO;

    @Resource
    private FancyDAO fancyDAO;

    private HashMap<Object, Object> params;

    @Override
    public void userUpdateFancy(String[] fancyStrings, Long userId) {
        logger.info("FancyServiceImpl userUpdateFancy begin...");
        if (fancyStrings != null && fancyStrings.length != 0) {
            //删除之前相关的interestUser
            fancyUserDAO.deleteByUserId(userId);

            for (String fancyName : fancyStrings) {

                Fancy fancyFromDB = getFancyByName(fancyName);
                FancyUser fancyUser = new FancyUser();
                fancyUser.setUserId(userId);

                if (fancyFromDB != null) {
                    // 如果该类型在数据库已存在，使用该类型的id
                    fancyUser.setFancyId(fancyFromDB.getId());

                } else {
                    // 如果该类型在数据库不存在，则新建一个
                    Fancy fancy = new Fancy();
                    fancy.setName(fancyName);

                    // TODO 以后再 redis 微服务中调用
//                    fancy.setIdentify(application.getBASE_FANCY_IDENTIFY());


                    // 用户自建
                    fancy.setType(FancyTypeAndStatus.USER_TYPE);
                    // 状态--开放
                    fancy.setStatus(FancyTypeAndStatus.OPEN_STATUS);
                    fancyDAO.insertSelective(fancy);
                    fancyUser.setFancyId(fancy.getId());
                }
                try {
                    //添加喜爱类型
                    fancyUserDAO.insertSelective(fancyUser);

                } catch (DuplicateKeyException e) {

                    System.out.println("\n重复添加喜爱类型，不做处理");
                }
            }
        }
    }

    public Fancy getFancyByName(String name) {
        params = new HashMap<>();
        params.put("name", name);
        return fancyDAO.selectOne(params);
    }
}
