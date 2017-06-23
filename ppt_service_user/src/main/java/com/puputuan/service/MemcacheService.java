package com.puputuan.service;

import com.puputuan.generic.userbase.model.UserBase;

/**
 * Created by chenzhuobin on 2017/6/8.
 */
public interface MemcacheService {
    public Boolean resetUserBase(String oldToken, UserBase userBase);

    UserBase getUserBaseByToken(String token);
}
