package com.puputuan.service.impl;

import com.puputuan.common.enums.ResultCode;
import com.puputuan.common.utils.TransactionHelper;
import com.puputuan.dao.UserDAO;
import com.puputuan.generic.permission.service.ShiroService;
import com.puputuan.generic.userbase.service.UserBaseService;
import com.puputuan.model.User;
import com.puputuan.model.jsonMedol.JSONUser;
import com.puputuan.service.MemcacheService;
import com.puputuan.service.UserLoginService;
import com.puputuan.service.UserService;
import com.puputuan.utils.CheckNullUtils;
import com.puputuan.utils.ConvertUtils;
import com.puputuan.utils.ResultUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenzhuobin on 2017/6/19.
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.INTERFACES)
public class UserLoginServiceImpl implements UserLoginService {

    private Logger logger = Logger.getLogger(UserLoginServiceImpl.class);

    @Resource
    private UserDAO userDAO;
    @Resource(name = "shiroService")
    private ShiroService shiroService;
    @Resource
    private TransactionHelper transactionHelper;
    @Resource(name = "userBaseService")
    private UserBaseService userBaseService;
    @Resource
    private MemcacheService memcacheService;

    @Resource
    private UserService userService;


    @Override
    public Map loginByAccount(String account, String password, Integer device) {
        logger.info("UserServiceImpl loginByAccount ...");
        Map<String, Object> resultMap = new HashMap();
        if (CheckNullUtils.checkParaNULL(account, password, device)) {
            //密码登录
            LoginComponent loginByAccount = new LoginComponent() {
                @Override
                public User getUser(Object... objects) {
                    Map<String, Object> param = new HashMap();
                    if (objects.length == 2) {
                        String account1 = (String) objects[0];
                        String password1 = (String) objects[1];
                        if (CheckNullUtils.checkParaNULL(password1)) {
                            param.put("phone", account1);
                            return userDAO.selectByParam(param);
                        }
                    }
                    return null;
                }

                @Override
                public Boolean isLogin(User user, Map innerResultMap, Object... objects) {
                    String password1 = (String) objects[1];
                    if (CheckNullUtils.checkParaNULL(password1)) {
                        if (user == null) {
                            ResultUtils.setParams(innerResultMap, ResultCode.CODE_700.code, ResultCode.CODE_700.msg);
                            return false;
                        }
                        if (user.getUserBase().getFreeze() == 1) {
                            ResultUtils.setParams(innerResultMap, ResultCode.CODE_700.code, ResultCode.CODE_700.msg);
                            return false;
                        }
                        //调用Shiro的登录功能
                        shiroService.login(user.getUserBase().getAccount(), password1, "app");
                        return true;
                    }else {
                        ResultUtils.setParams(innerResultMap, ResultCode.CODE_401.code, ResultCode.CODE_401.msg);
                        return false;
                    }
                }
            };

            resultMap = login(device, loginByAccount, account, password);
            return resultMap;

        }
        return ResultUtils.setParams(resultMap, ResultCode.CODE_401.code, ResultCode.CODE_401.msg);
    }

    @Override
    public Map loginByWechat(String account, Integer device) {
        logger.info("UserServiceImpl loginByWechat ...");
        Map<String, Object> resultMap = new HashMap();
        if (CheckNullUtils.checkParaNULL(account, device)) {
            LoginComponent loginByWechat =new LoginComponent() {
                @Override
                public User getUser(Object... objects) {
                    //微信登录
                    Map<String, Object> param = new HashMap();
                    param.put("wechatId", objects[0]);
                    return userDAO.selectByParam(param);
                }

                @Override
                public Boolean isLogin(User user,Map innerResultMap,Object... objects){
                    if (user == null) {
                        //user为空，第一次用微信登录,需要先注册个人信息
                        ResultUtils.setParams(innerResultMap, ResultCode.CODE_714.code, ResultCode.CODE_714.msg);
                        return false;
                    }
                    if (user.getUserBase().getFreeze() == 1) {
                        ResultUtils.setParams(innerResultMap, ResultCode.CODE_706.code, ResultCode.CODE_706.msg);
                        return false;
                    }
                    return true;
                }
            };
            resultMap = login(device, loginByWechat, account);
            return resultMap;
        }
        return ResultUtils.setParams(resultMap, ResultCode.CODE_401.code, ResultCode.CODE_401.msg);
    }



    /**
     * 通过实现接口进行login判断
     * @param device
     * @param loginComponent 需要实现的内部接口
     * @param objects
     * @return
     */
    private Map login(Integer device, LoginComponent loginComponent, Object... objects) {
        logger.info("UserServiceImpl login...");
        TransactionStatus transactionStatus = transactionHelper.start();
        Map<String, Object> resultMap = new HashMap();
        String oldToken = null;
        if (CheckNullUtils.checkParaNULL(device)) {
            try {
                User user = loginComponent.getUser(objects);
                Boolean isLogin = loginComponent.isLogin(user,resultMap,objects);
                if (!isLogin) {
                    transactionHelper.rollback(transactionStatus);
                    return resultMap;
                }

                //保存oldToken 方便 后面操作
                oldToken = user.getUserBase().getToken();

                userBaseService.updateUserBaseByUser(user, device);
                userDAO.updateByUserId(user);

                //根据oldToken去掉旧的user  user信息放进memcache
                memcacheService.resetUserBase(oldToken, user.getUserBase());


//        //获取图片
                // TODO 以后从其他微服务获取
//        ossFileUtils.getPhotoList(user.getAlbum(), null);

                transactionHelper.commit(transactionStatus);

                //resultMap.put("object", user);
                userService.setUserInterestJobFancy(user);
                JSONUser jsonUser = ConvertUtils.convertUser(user);
                resultMap.put("object", jsonUser);
                // TODO 以后从其他微服务中获取
//        resultMap.put("haveRunningGroup", groupService.getRunningGroup(user.getUserId()) != null ? true : false);
                resultMap.put("authorization", userService.getAuthorizationFromUser(user));

                return ResultUtils.setParams(resultMap, ResultCode.CODE_200.code, ResultCode.CODE_200.msg);

            } catch (UnknownAccountException uae) {
                logger.error("UserControllerImpl login error " + uae);
                transactionHelper.rollback(transactionStatus);
                return ResultUtils.setParams(resultMap, ResultCode.CODE_700.code, ResultCode.CODE_700.msg);
            } catch (IncorrectCredentialsException ice) {
                logger.error("UserControllerImpl login error " + ice);
                transactionHelper.rollback(transactionStatus);
                return ResultUtils.setParams(resultMap, ResultCode.CODE_700.code, ResultCode.CODE_700.msg);
            } catch (LockedAccountException lae) {
                logger.error("UserControllerImpl login error " + lae);
                transactionHelper.rollback(transactionStatus);
                return ResultUtils.setParams(resultMap, ResultCode.CODE_706.code, ResultCode.CODE_706.msg);
            } catch (IllegalArgumentException iae) {
                logger.error("UserControllerImpl login error " + iae);
                transactionHelper.rollback(transactionStatus);
                return ResultUtils.setParams(resultMap, ResultCode.CODE_703.code, ResultCode.CODE_703.msg);
            } catch (ExcessiveAttemptsException e) {
                logger.error("UserControllerImpl login error " + e);
                transactionHelper.rollback(transactionStatus);
                return ResultUtils.setParams(resultMap, ResultCode.CODE_710.code, ResultCode.CODE_710.msg);
            } catch (Exception ex) {
                logger.error("UserControllerImpl login error " + ex);
                transactionHelper.rollback(transactionStatus);
                return ResultUtils.setParams(resultMap, ResultCode.CODE_500.code, ResultCode.CODE_500.msg);
            }
        }
        return ResultUtils.setParams(resultMap, ResultCode.CODE_401.code, ResultCode.CODE_401.msg);
    }

    interface LoginComponent {
        /**
         * 通过可变字段获取对应的user
         * @param objects
         * @return
         * login函数需要在外部引用用user 所以这里需要定义如何获取user
         */

        User getUser(Object... objects);

        /**
         *通过user进行登录判断
         * @param user
         * @param resultMap
         * @param objects  扩充字段方便以后进行有效的兼容，与getUser函数中的objects相同
         * @return
         * 返回
         * true： 则登录成功
         * false：则登录失败
         */
        Boolean isLogin(User user,Map resultMap,Object... objects);
    }
}
