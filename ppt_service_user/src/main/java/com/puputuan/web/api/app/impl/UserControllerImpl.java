package com.puputuan.web.api.app.impl;

import com.puputuan.common.enums.ResultCode;
import com.puputuan.controller.base.BaseController;
import com.puputuan.generic.userbase.model.UserBase;
import com.puputuan.model.User;
import com.puputuan.service.UserLoginService;
import com.puputuan.service.UserService;
import com.puputuan.web.api.app.UserController;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.util.Map;


/**
 * Created by jian on 25/3/16.
 */
@RestController
public class UserControllerImpl extends BaseController implements UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserLoginService userLoginService;

    /**
     * 添加用户时的用户标识，从 600000 开始递增
     */
    private static final Long BASE_IDENTIFY = 600000L;

    private static final String SHIELD_PHONE_ON = "1";

    // 返回super like的次数
    private final static String NUM_SUPER_LIKE = "NUM_SUPER_LIKE";


    @Override
    public Object login(String account, String password,String code, Integer type, Integer device) {
        logger.info("UserControllerImpl login begin ...");
        Map resultMap;
        if (checkParaNULL(account, type, device)) {
            switch (type) {
                case 1:
                    //密码登录
                    resultMap =  userLoginService.loginByAccount(account,password,device);
                    if (resultMap.get("code").equals( ResultCode.CODE_200.code)){
                        return sendResult(resultMap,true);
                    }
                    return sendResult(resultMap);
                case 2:
                    //微信登录
                    resultMap =  userLoginService.loginByWechat(account,device);
                    if ((resultMap.get("code").equals(ResultCode.CODE_714.code))) {
                        return sendResult(resultMap, true);
                    } else if ((resultMap.get("code").equals( ResultCode.CODE_200.code))) {
                        return sendResult(resultMap, true);
                    }
                    return sendResult(resultMap);
                default:

            }
        }
        return sendResult(ResultCode.CODE_401.code, ResultCode.CODE_401.msg, null);
    }

    @Override
    public Object register(@ModelAttribute User user, @ModelAttribute UserBase userBase, String code, @RequestParam("uploadfile") CommonsMultipartFile file) {
        logger.info("UserControllerImpl login begin ...");
        Map resultMap = userService.register(user,userBase,code,file);
        if (resultMap.get("code").equals( ResultCode.CODE_200.code)){
            return sendResult(resultMap,true);
        }
        return sendResult(resultMap);
    }

    @Override
    public Object resetPassword(String account, String password, String code) {
        Map resultMap = userService.resetPassword(account,password,code);
        if (resultMap.get("code").equals( ResultCode.CODE_200.code)){
            return sendResult(resultMap,true);
        }
        return sendResult(resultMap);
    }

    @Override
    public Object getInfo() {
        Map resultMap = userService.getInfo();
        if (resultMap.get("code").equals( ResultCode.CODE_200.code)){
            return sendResult(resultMap,true);
        }
        return sendResult(resultMap);
    }

    @Override
    public Object updateInfo(@ModelAttribute User user, Long avatarId, String[] jobStrings, String[] interestStrings, String[] fancyStrings) {
        Map resultMap = userService.updateInfo(user,avatarId,jobStrings,interestStrings,fancyStrings);
        if (resultMap.get("code").equals( ResultCode.CODE_200.code)){
            return sendResult(resultMap,true);
        }
        return sendResult(resultMap);
    }

    @Override
    public Object updatePassword(String oldPassword, String password) {
        Map resultMap = userService.updatePassword(oldPassword,password);
        return sendResult(resultMap);
    }

    @Override
    public Object search(String identify) {
        Map resultMap = userService.searchByIdentify(identify);
        return sendResult(resultMap);
    }

    @Override
    public Object bindPhone(String phone, String code, String password) {
        Map resultMap = userService.bindPhone(phone,code,password);
        return sendResult(resultMap);
    }

    @Override
    public Object bindWeChat(String wechatId) {
        Map resultMap = userService.bindWeChat(wechatId);
        return sendResult(resultMap);
    }

    @Override
    public Object complainUser(String identify, Integer type, String content) {
        Map resultMap = userService.complainUser(identify,type,content);
        return sendResult(resultMap);
    }

    @Override
    public Object shieldPhoneOn(String ifOn) {
        Map resultMap = userService.shieldPhoneOn(ifOn);
        return sendResult(resultMap);
    }

    @Override
    public Object ifShieldPhoneOn() {
        Map resultMap = userService.ifShieldPhoneOn();
        return sendResult(resultMap);
    }

    @Override
    public Object searchUserByPhones(String[] phones) {
        Map resultMap = userService.searchUserByPhones(phones);
        return sendResult(resultMap);
    }

    @Override
    public Object searchUserSuperLikeTimes() {
        Map resultMap = userService.searchUserSuperLikeTimes();
        return sendResult(resultMap);
    }
}
