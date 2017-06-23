package com.puputuan.web.api.ctrl.impl;


/**
 * Created by jian on 16/3/15.
 */

import com.puputuan.common.enums.ExceptionMessage;
import com.puputuan.common.enums.ResultCode;
import com.puputuan.controller.base.BaseController;
import com.puputuan.generic.permission.service.ShiroService;
import com.puputuan.generic.userbase.model.UserBase;
import com.puputuan.model.Admin;
import com.puputuan.model.AdminLoginHis;
import com.puputuan.service.MemcacheService;
import com.puputuan.service.SimpleManageService;
import com.puputuan.web.api.ctrl.CommonController;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CommonControllerImpl extends BaseController implements CommonController {

    @Resource(name = "shiroService")
    private ShiroService shiroService;

    @Resource
    private SimpleManageService simpleManageService;

    @Resource
    private MemcacheService memcacheService;


    @Override
    public ModelAndView login() {
        try {
            if (shiroService.getLoginUserBase() != null) {
                return sendResult("redirect:/", null);
            }
        } catch (UnknownAccountException e) {
            return sendResult(ResultCode.CODE_200.code, "login.jsp", ResultCode.CODE_200.msg, null);
        }
        return sendResult(ResultCode.CODE_200.code, "login.jsp", ResultCode.CODE_200.msg, null);
    }

    @Override
    public ModelAndView login(String account, String password) {
        if (checkParaNULL(account, password)) {
            Map<String, Object> resultMap = new HashMap<>();
            try {
                UserBase userBase = (UserBase) shiroService.login(account, password, "ctrl");
                if (userBase != null) {
                    //将用户基本信息存进session
                    shiroService.setLoginUserBase(userBase);
                    Admin admin = (Admin) simpleManageService.initObjectDAO("adminDAO").invokeMethodByName("selectByUserId",new Class[]{Long.class},userBase.getId());
                    if (admin != null) {
                        //将管理员信息存进session
                        shiroService.setLoginUser(admin);
                        addLoginHis(admin);
                        resultMap.put("object", admin);
                        return sendResult("redirect:/", resultMap);
                    }
                    shiroService.logout();
                    return sendResult(ResultCode.CODE_700.code, "login.jsp", ResultCode.CODE_700.msg, null);
                }
                return sendResult(ResultCode.CODE_700.code, "login.jsp", ResultCode.CODE_700.msg, null);
            } catch (UnknownAccountException uae) {
                logger.error(ExceptionMessage.UnknownAccountException.getMessage(),uae);
                return sendResult(ResultCode.CODE_700.code, "login.jsp", ResultCode.CODE_700.msg, null);
            } catch (IncorrectCredentialsException ice) {
                logger.error(ExceptionMessage.IncorrectCredentialsException.getMessage(),ice);
                return sendResult(ResultCode.CODE_701.code, "login.jsp", ResultCode.CODE_701.msg, null);
            } catch (LockedAccountException lae) {
                logger.error(ExceptionMessage.LockedAccountException.getMessage(),lae);
                return sendResult(ResultCode.CODE_706.code, "login.jsp", ResultCode.CODE_706.msg, null);
            } catch (IllegalArgumentException iae) {
                logger.error(ExceptionMessage.IllegalAccessException.getMessage(),iae);
                return sendResult(ResultCode.CODE_703.code, "login.jsp", ResultCode.CODE_703.msg, null);
            } catch (ExcessiveAttemptsException e) {
                logger.error(ExceptionMessage.ExcessiveAttemptsException.getMessage(),e);
                return sendResult(ResultCode.CODE_710.code, "login.jsp", ResultCode.CODE_710.msg, null);
            } catch (Exception ex) {
                logger.error(ExceptionMessage.Exception.getMessage(),ex);
                return sendResult(ResultCode.CODE_500.code, "login.jsp", ResultCode.CODE_500.msg, null);
            }
        }
        return sendResult(ResultCode.CODE_401.code, "login.jsp", ResultCode.CODE_401.msg, null);
    }

    private void addLoginHis(Admin admin){

        AdminLoginHis adminLoginHis = new AdminLoginHis();
        adminLoginHis.setUserId(admin.getUserId());
        adminLoginHis.setNickName(admin.getNickName());
        adminLoginHis.setCreateTime(new Date());

        //动态调用dao
        //动态调用方法
        simpleManageService.initObjectDAO("adminLoginHisDAO").addObject(adminLoginHis);
    }

    @Override
    public ModelAndView logout() {
        if (shiroService.getLoginUserBase() != null && shiroService.getLoginUser() != null) {
            shiroService.logout();
        }
        return sendResult("redirect:/login", null);
    }

    @Override
    public Object checkLogin(String token) {
        UserBase userBase = memcacheService.getUserBaseByToken(token);
        if (userBase!=null){
            Map<String, Object> resultMap = new HashedMap();
            resultMap.put("object",userBase);
            return sendResult(ResultCode.CODE_200.code,ResultCode.CODE_200.msg,resultMap,true,"password");
        }
        return sendResult(ResultCode.CODE_401.code,ResultCode.CODE_401.msg,null);
    }
}
