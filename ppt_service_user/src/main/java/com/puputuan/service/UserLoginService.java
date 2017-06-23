package com.puputuan.service;

import java.util.Map;

/**
 * Created by chenzhuobin on 2017/6/19.
 */
public interface UserLoginService {
    //密码登录
    Map loginByAccount(String account, String password, Integer device);

    //微信登录
    Map loginByWechat(String account, Integer device);
}
