package com.puputuan.service.impl;

import com.puputuan.generic.userbase.model.UserBase;
import com.puputuan.service.MemcacheService;
import com.puputuan.utils.MemcacheUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by chenzhuobin on 2017/6/8.
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.INTERFACES)
public class MemcacheServiceImpl implements MemcacheService {
    @Resource
    private MemcacheUtils memcacheUtils;

    @Override
    public Boolean resetUserBase(String oldToken, UserBase userBase) {
        Boolean isSuccess;
        memcacheUtils.remove(oldToken);
        isSuccess = memcacheUtils.add(userBase.getToken(),userBase);
        return isSuccess;
    }

    @Override
    public UserBase getUserBaseByToken(String token) {
        return (UserBase) memcacheUtils.get(token);
    }


}
