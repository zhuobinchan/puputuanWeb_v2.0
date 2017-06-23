package com.puputuan.service.impl;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.puputuan.common.constant.SexType;
import com.puputuan.common.enums.ResultCode;
import com.puputuan.common.utils.TransactionHelper;
import com.puputuan.config.mine.ConfigInfo;
import com.puputuan.dao.*;
import com.puputuan.generic.permission.service.ShiroService;
import com.puputuan.generic.userbase.model.UserBase;
import com.puputuan.generic.userbase.service.UserBaseService;
import com.puputuan.model.*;
import com.puputuan.model.jsonMedol.Authorization;
import com.puputuan.model.jsonMedol.JSONUser;
import com.puputuan.service.*;
import com.puputuan.utils.*;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by chenzhuobin on 2017/5/26.
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.INTERFACES)
public class UserServiceImpl implements UserService {
    private Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Resource
    private UserDAO userDAO;

    @Resource(name = "shiroService")
    private ShiroService shiroService;

    @Resource
    private TransactionHelper transactionHelper;

    @Resource
    private InterestDAO interestDao;

    @Resource
    private JobDAO jobDAO;

    @Resource
    private FancyDAO fancyDAO;

    @Resource
    private ConfigInfo configInfo;

    @Resource(name = "userBaseService")
    private UserBaseService userBaseService;

    @Resource
    private JobService jobService;

    @Resource
    private InterestService interestService;

    @Resource
    private FancyService fancyService;

    @Resource
    private UserSettingDAO userSettingDAO;

    @Resource
    private ComplainUserDAO complainUserDAO;

    @Resource
    private UserSuperLikeDAO userSuperLikeDAO;

    @Resource
    private UserSuperLikeConfigDAO userSuperLikeConfigDAO;



    public void setUserInterestJobFancy(User user) {
        List<Interest> interesList = interestDao.selectParamWithSystem(user.getUserId());
        List<Job> joblist = jobDAO.selectParamWithSystem(user.getUserId());
        List<Fancy> fancyList = fancyDAO.selectParamWithSystem(user.getUserId());
        List<Interest> userInterest = user.getInterestList();
        List<Job> userJob = user.getJobList();
        List<Fancy> userFancy = user.getFancyList();

        if (null != userInterest) {
            for (Interest in : interesList) {
                for (Interest ui : userInterest) {
                    if (in.getId().equals(ui.getId())) {
                        in.setHaveSelect(true);
                    }
                }
            }
        }

        if (null != userJob) {
            for (Job j : joblist) {
                for (Job uj : userJob) {
                    if (j.getId().equals(uj.getId())) {
                        j.setHaveSelect(true);
                    }
                }
            }
        }

        if (null != userFancy) {
            for (Fancy f : fancyList) {
                for (Fancy uf : userFancy) {
                    if (f.getId().equals(uf.getId())) {
                        f.setHaveSelect(true);
                    }
                }
            }
        }
        user.setInterestList(interesList);
        user.setJobList(joblist);
        user.setFancyList(fancyList);
    }

    //调用前需要确保user有userBase实例变量
    public Authorization getAuthorizationFromUser(User user) {
        Authorization authorization = new Authorization();
        authorization.setAccount(user.getPhone());
        authorization.setToken(user.getUserBase().getToken());
        return authorization;
    }

    @Override
    public Map register(User userReg, UserBase userBaseReg, String code, CommonsMultipartFile file) {
        logger.info("UserServiceImpl register ...");
        Map<String, Object> resultMap = new HashMap();
        if (CheckNullUtils.checkParaNULL(userBaseReg, userBaseReg.getAccount(), userBaseReg.getPassword(), userReg, userReg.getBirthday(),
                userReg.getSex(), userReg.getNickName(), code, file)) {

            //TODO 修改注册流程
            Map<String, Object> param = new HashMap<>();
            param.put("phone", userBaseReg.getAccount());
            User user = userDAO.selectOne(param);
            if (user == null) {
                /** 开启事务 */
                TransactionStatus transactionStatus = transactionHelper.start();

                // TODO 以后这里使用其他微服务进行短信验证
//                if( configInfo.isSmsOpen() ) {
//                    //获取短信验证码信息
//                    ResultCode resultCode = checkSmsCode(userBaseReg.getAccount(), code);
//                    if (resultCode != ResultCode.CODE_200) {
//                        /** 回滚事务 */
//                        transactionHelper.rollback(transactionStatus);
//                        return sendResult(resultCode.code, resultCode.msg, null);
//                    }
//                }

                UserBase userBase = new UserBase();
//                userBase.setAccount("popoteam_user_" + System.currentTimeMillis());
                userBase.setAccount(configInfo.getAccountPrex() + System.currentTimeMillis());
                userBase.setToken(UuidUtils.generateUuid());
                userBase.setPassword(userBaseReg.getPassword());

                try {
                    //添加用户基本信息
                    userBaseService.add(userBase);

                    // TODO file头像不设值 以后调用其他微服务进行调用
//                    File pojoFile = ossFileUtils.uploadFile(userBase.getId(), file, (long) 0);
//                    if (null == pojoFile) {
//                        /** 回滚事务 */
//                        transactionHelper.rollback(transactionStatus);
//                        return sendResult(ResultCode.CODE_500.code, ResultCode.CODE_500.msg, null);
//                    }
//
//                    //插入相册表,设置为头像
//                    Photo photo = new Photo();
//                    photo.setImageId(pojoFile.getId());
//                    photo.setUserId(userBase.getId());
////                    photo.setIsDelete(false);
//                    photo.setIsAvatar(true);
//                    photo.setImage(pojoFile);
//                    photoService.addPhoto(photo);


                    user = new User();
                    user.setUserId(userBase.getId());
                    user.setPhone(userBaseReg.getAccount());

                    // TODO 从其他微服务中获取 redis
//                    user.setIdentify(application.getBASE_USER_IDENTIFY() + "");
                    user.setBirthday(userReg.getBirthday());
                    user.setConstellation(ConstellationUtils.getConstellation(user.getBirthday()));
                    //设置性别只能是 M 或 F
                    user.setSex(userReg.getSex().equals(SexType.MALE) ? SexType.MALE : SexType.FEMALE);
                    // 过滤器没起作用，手动转码emji表情
                    user.setNickName(EmojiCharacterUtil.emojiConvert1(userReg.getNickName()));
                    // 用户昵称对应的拼音，过滤emoji表情
                    String filterEmojiNickName = EmojiCharacterUtil.filter(EmojiCharacterUtil.emojiRecovery2(userReg.getNickName()));
                    user.setNickNameChr(PinyinHelper.convertToPinyinString(filterEmojiNickName.equals("") ? "#" : filterEmojiNickName
                            , "", PinyinFormat.WITHOUT_TONE));
                    user.setWechatId(userReg.getWechatId());

                    // TODO 文件类型照片先 不处理 以后从其他服务中进行处理
//                    user.setHeadId(photo.getImageId());
                    user.setUserBase(userBase);

                    // TODO 文件类型照片先 不处理 以后从其他服务中进行处理
//                    List <Photo> album = new ArrayList<>();
//                    album.add(photo);
//                    user.setAlbum(album);

                    //添加用户详细信息
                    userDAO.insertSelective(user);

                    /**  注册环信 注册成功之后再向数据库插入数据 */
                    // TODO 环信服务，以后使用微服务进行管理
//                    //查询用户是否存在
//                    boolean isUserExist = HuanXinUserService.userExistOrNot(userBase.getAccount());
//                    if (!isUserExist) {
//                        //如果环信用户不存在，则新建一个
//                        HuanXinUserService.createNewIMUser(userBase.getAccount(), user.getNickName());
//                    }

                    /** 提交事务 */
                    transactionHelper.commit(transactionStatus);
                } catch (AuthenticationException e) {
                    logger.error("UserControllerImpl register error " + e);
                    /** 回滚事务 */
                    transactionHelper.rollback(transactionStatus);
                    return ResultUtils.setParams(resultMap, ResultCode.CODE_500.code, ResultCode.CODE_500.msg);

                } catch (IllegalArgumentException e) {
                    logger.error("UserControllerImpl register error " + e);
                    /** 回滚事务 */
                    transactionHelper.rollback(transactionStatus);
                    return ResultUtils.setParams(resultMap, ResultCode.CODE_500.code, ResultCode.CODE_500.msg);
                } catch (PinyinException e) {
                    //转换拼音异常
                    logger.error("UserControllerImpl register error " + e);
                    /** 回滚事务 */
                    transactionHelper.rollback(transactionStatus);
                    return ResultUtils.setParams(resultMap, ResultCode.CODE_500.code, ResultCode.CODE_500.msg);
                }
                // TODO 以后环信微服务 再修改
//                catch (HuanXinUserException e) {
//                    logger.error("UserControllerImpl register error " + e);
//                    /** 回滚事务 */
//                    transactionHelper.rollback(transactionStatus);
//                    return sendResult(ResultCode.CODE_600.code, e.getMessage(), null);
//                }
                catch (Exception e) {
                    logger.error("UserControllerImpl register error " + e);
                    /** 回滚事务 */
                    transactionHelper.rollback(transactionStatus);
                    return ResultUtils.setParams(resultMap, ResultCode.CODE_500.code, ResultCode.CODE_500.msg);
                }

                //调用Shiro的登录功能
                shiroService.login(user.getUserBase().getAccount(), userBaseReg.getPassword(), "app");

                // TODO test by simon at 2016/07/29
                //resultMap.put("object", user);
                this.setUserInterestJobFancy(user);
                JSONUser jsonUser = ConvertUtils.convertUser(user);
                resultMap.put("object", jsonUser);

                resultMap.put("authorization", getAuthorizationFromUser(user));

                return ResultUtils.setParams(resultMap, ResultCode.CODE_200.code, ResultCode.CODE_200.msg);
            }
            return ResultUtils.setParams(resultMap, ResultCode.CODE_702.code, ResultCode.CODE_702.msg);
        }
        return ResultUtils.setParams(resultMap, ResultCode.CODE_401.code, ResultCode.CODE_401.msg);
    }

    @Override
    @Transactional(timeout = 5000)
    public Map resetPassword(String account, String password, String code) {
        logger.info("UserServiceImpl resetPassword ...");
        Map<String, Object> resultMap = new HashMap<>();
        if (CheckNullUtils.checkParaNULL(account, password, code)) {
            // TODO 以后调用 信验证微服务
//            if( configInfo.isSmsOpen() ) {
//                //获取短信验证码信息
//                ResultCode resultCode = checkSmsCode(account, code);
//                if (resultCode != ResultCode.CODE_200) {
//                    return sendResult(resultCode.code, resultCode.msg, null);
//                }
//            }

            Map<String, Object> param = new HashMap<>();
            param.put("phone", account);
            User user = userDAO.selectByParam(param);

            //用户不存在
            if (null == user) {
                return ResultUtils.setParams(resultMap, ResultCode.CODE_700.code, ResultCode.CODE_700.msg);
            }

            user.getUserBase().setToken(UuidUtils.generateUuid());
            user.getUserBase().setPassword(password);
            userBaseService.update(user.getUserBase());

            //调用Shiro的登录功能
            shiroService.login(user.getUserBase().getAccount(), password, "app");

            // TODO 以后调用文件管理 微服务
//            //获取图片
//            ossFileUtils.getPhotoList(user.getAlbum(), null);
            this.setUserInterestJobFancy(user);
            JSONUser jsonUser = ConvertUtils.convertUser(user);
            resultMap.put("object", jsonUser);
            resultMap.put("authorization", getAuthorizationFromUser(user));
            return ResultUtils.setParams(resultMap, ResultCode.CODE_200.code, ResultCode.CODE_200.msg);
        }
        return ResultUtils.setParams(resultMap, ResultCode.CODE_401.code, ResultCode.CODE_401.msg);
    }

    @Override
    public Map getInfo() {
        logger.info("UserServiceImpl getInfo ...");
        Map<String, Object> resultMap = new HashMap<>();

        // TODO 日活跃量暂时可能不用到，以后可能在微服务中调用
//        userService.updateUserDailyLiving(shiroService.getLoginUserBase().getId());
        try {
            //查询用户信息
            User user = userDAO.selectByUserId(shiroService.getLoginUserBase().getId());

//            User user = userService.getByUserIdWithSystem(shiroService.getLoginUserBase().getId());
            //获取头像
//            ossFileUtils.getUserHead(user, null);

            // TODO 以后微服务中调用
            //获取图片
//            ossFileUtils.getPhotoList(user.getAlbum(), null);


            this.setUserInterestJobFancy(user);
            //resultMap.put("object", user);
            // TODO test by simon at 2016/07/29
            JSONUser jsonUser = ConvertUtils.convertUser(user);
            resultMap.put("object", jsonUser);

            // TODO 以后微服务中调用
//            resultMap.put("haveRunningGroup", groupService.getRunningGroup(user.getUserId()) != null ? true : false);

            return ResultUtils.setParams(resultMap, ResultCode.CODE_200.code, ResultCode.CODE_200.msg);  // TODO mark by simon 这个不加true的话，会过滤掉 token 和 password 两个字段

        } catch (UnknownAccountException uae) {
            logger.error("UserControllerImpl getInfo error " + uae);
            return ResultUtils.setParams(resultMap, ResultCode.CODE_700.code, ResultCode.CODE_700.msg);
        } catch (IncorrectCredentialsException ice) {
            logger.error("UserControllerImpl getInfo error " + ice);
            ice.printStackTrace();
            return ResultUtils.setParams(resultMap, ResultCode.CODE_701.code, ResultCode.CODE_701.msg);
        } catch (LockedAccountException lae) {
            logger.error("UserControllerImpl getInfo error " + lae);
            return ResultUtils.setParams(resultMap, ResultCode.CODE_706.code, ResultCode.CODE_706.msg);
        } catch (IllegalArgumentException iae) {
            logger.error("UserControllerImpl getInfo error " + iae);
            return ResultUtils.setParams(resultMap, ResultCode.CODE_703.code, ResultCode.CODE_703.msg);
        } catch (ExcessiveAttemptsException e) {
            logger.error("UserControllerImpl getInfo error " + e);
            return ResultUtils.setParams(resultMap, ResultCode.CODE_710.code, ResultCode.CODE_710.msg);
        } catch (Exception ex) {
            logger.error("UserControllerImpl getInfo error " + ex);
            return ResultUtils.setParams(resultMap, ResultCode.CODE_500.code, ResultCode.CODE_500.msg);
        }
    }

    @Override
    @Transactional(timeout = 5000)
    public Map updateInfo(User user, Long avatarId, String[] jobStrings, String[] interestStrings, String[] fancyStrings) {
        logger.info("UserServiceImpl updateInfo ...");

        Map resultMap = new HashMap();

        boolean needUpdateUser = false;

        User userUpdate = new User();
        userUpdate.setUserId(shiroService.getLoginUserBase().getId());


        // TODO 头像 在以后文件管理的微服务中调用
//        Long imageId;
//        if (null != avatarId) {
//            imageId = updateAvatar(avatarId);
//            //输入的avatarId错误,返回失败响应
//            if (null == imageId) {
//                return sendResult(ResultCode.CODE_401.code, ResultCode.CODE_401.msg, null);
//            }
//
//            userUpdate.setHeadId(imageId);
//            needUpdateUser = true;
//        }

        if (CheckNullUtils.checkParaNULL(user)) {
            // TODO: 16/8/27  这里要考虑出现异常时,之前的照片数据如何回退,事务能否在这里catch异常后回退?
            try {
                //是否需要更新，当所有字段都为空时不更新
//                boolean needUpdateUser = false;
//                for (Field field : user.getClass().getDeclaredFields()) {
//                    field.setAccessible(true);
//                    //有一个字段不为空，则要更新，否则不更新
//                    if (field.get(user) != null) {
//                        needUpdateUser = true;
//                    }
//                }

                /**
                 * 如果更改了用户名，则对应更新环信账户的用户名
                 * 同时还需要更新nickNameChar字段
                 * */
                if (user.getNickName() != null && !user.getNickName().equals("")) {
//                    user.setNickName(EmojiCharacterUtil.emojiConvert1(user.getNickName()));

                    // TODO 环信消息推送，以后使用微服务 管理
//                    HuanXinUserService.modifyIMUserNickNae(shiroService.getLoginUserBase().getAccount(), user.getNickName());

                    userUpdate.setNickName(user.getNickName());
                    // 用户昵称对应的拼音，过滤emoji表情
                    String filterEmojiNickName = EmojiCharacterUtil.filter(EmojiCharacterUtil.emojiRecovery2(user.getNickName()));
                    userUpdate.setNickNameChr(PinyinHelper.convertToPinyinString(filterEmojiNickName.equals("") ? "#" : filterEmojiNickName
                            , "", PinyinFormat.WITHOUT_TONE));

                    needUpdateUser = true;
                }
            } catch (PinyinException e) {
                logger.error("UserControllerImpl updateInfo error " + e);
                //转换拼音异常
                e.printStackTrace();
                return ResultUtils.setParams(resultMap, ResultCode.CODE_500.code, ResultCode.CODE_500.msg);
            }
            // TODO 环信在微服务中获取
//            catch (HuanXinUserException e) {
//                logger.error("UserControllerImpl updateInfo error " + e);
//                e.printStackTrace();
//                return sendResult(ResultCode.CODE_600.code, e.getMessage(), null);
//            }
            catch (Exception e) {
                logger.error("UserControllerImpl updateInfo error " + e);
                e.printStackTrace();
                return ResultUtils.setParams(resultMap, ResultCode.CODE_500.code, ResultCode.CODE_500.msg);
            }

            if (user.getSign() != null) {
                userUpdate.setSign(user.getSign());
                needUpdateUser = true;
            }

            /** 如果生日不为空，则计算出星座 */
            if (user.getBirthday() != null) {
                userUpdate.setBirthday(user.getBirthday());
                userUpdate.setConstellation(ConstellationUtils.getConstellation(user.getBirthday()));
                needUpdateUser = true;
            }

            if (user.getProvinceId() != null) {
                userUpdate.setProvinceId(user.getProvinceId());
                needUpdateUser = true;
            }

            if (user.getProvince() != null) {
                userUpdate.setProvince(user.getProvince());
                needUpdateUser = true;
            }

            if (user.getCityId() != null) {
                userUpdate.setCityId(user.getCityId());
                needUpdateUser = true;
            }

            if (user.getCity() != null) {
                userUpdate.setCity(user.getCity());
                needUpdateUser = true;
            }

            if (user.getAreaId() != null) {
                userUpdate.setAreaId(user.getAreaId());
                needUpdateUser = true;
            }

            if (user.getArea() != null) {
                userUpdate.setArea(user.getArea());
                needUpdateUser = true;
            }
        }

        if (needUpdateUser) {
            //更新数据库记录
            userDAO.updateByUserId(userUpdate);
        }
        Long shiroUserId = shiroService.getLoginUserBase().getId();

        //step2：若职业不为空，更新职业信息
        jobService.userUpdateJob(jobStrings, shiroUserId);

        //step3：若兴趣不为空，更新兴趣信息
        interestService.userUpdateInterest(interestStrings, shiroUserId);

        // 更新喜爱夜蒲类型
        fancyService.userUpdateFancy(fancyStrings, shiroUserId);

        //个人信息更新后把所有信息重新返回给app,用于刷新界面
        return getInfo();
    }

    @Override
    public Map updatePassword(String oldPassword, String password) {
        logger.info("UserServiceImpl updatePassword begin...");
        Map resultMap = new HashMap();
        if (CheckNullUtils.checkParaNULL(password)) {
            /** 开启事务 */
            TransactionStatus transactionStatus = transactionHelper.start();
            try {
                // 判断旧密码是否正确
                boolean right = userBaseService.checkOldPassword(shiroService.getLoginUserBase().getAccount(), oldPassword);
                if (right) {
                    /** 更新蒲蒲团的账户的密码*/
                    userBaseService.updatePassword(shiroService.getLoginUserBase().getAccount(), password);
                    /** 提交事务 */
                    transactionHelper.commit(transactionStatus);

                    return ResultUtils.setParams(resultMap, ResultCode.CODE_200.code, ResultCode.CODE_200.msg);
                } else {
                    transactionHelper.rollback(transactionStatus);
                    return ResultUtils.setParams(resultMap, ResultCode.CODE_700.code, ResultCode.CODE_700.msg);
                }

            } catch (Exception e) {
                logger.error("UserControllerImpl updatePassword error " + e);
                /** 回滚事务 */
                transactionHelper.rollback(transactionStatus);
                return ResultUtils.setParams(resultMap, ResultCode.CODE_500.code, ResultCode.CODE_500.msg);
            }
        }
        return ResultUtils.setParams(resultMap, ResultCode.CODE_401.code, ResultCode.CODE_401.msg);
    }

    @Override
    public Map searchByIdentify(String identify) {
        logger.info("UserServiceImpl searchByIdentify begin...");
        Map<String, Object> resultMap = new HashMap<>();

        if (CheckNullUtils.checkParaNULL(identify)) {
            Map<String, Object> param = new HashMap<>();

//            param.put("nickName", name);
            if (identify.length() == 11) {
                param.put("phone", "+86" + identify);
            } else {
                param.put("identify", identify);
            }
            User user = userDAO.selectByParam(param);
            if (null == user) {
                return ResultUtils.setParams(resultMap, ResultCode.CODE_722.code, ResultCode.CODE_722.msg);
            }

            if (identify.length() == 11) {
                param.clear();
                param.put("userId", user.getUserId());
                UserSetting userSetting = userSettingDAO.selectOne(param);
                // 判断当前手机号码是否进行屏蔽
                if (null != userSetting && userSetting.getShieldPhoneOn() == 0) {
                    return ResultUtils.setParams(resultMap, ResultCode.CODE_200.code, ResultCode.CODE_200.msg);
                }
            }

            //获取头像
//            ossFileUtils.getUserHead(user, null);

            // TODO 以后从文件微服务中获取
//            //获取图片
//            ossFileUtils.getPhotoList(user.getAlbum(), null);

            //查询用户信息
            // TODO test by simon at 2016/08/03
            //resultMap.put("object", user);
            this.setUserInterestJobFancy(user);
            JSONUser jsonUser = ConvertUtils.convertUser(user);
            resultMap.put("object", jsonUser);

            param.clear();
            //查询是否是好友
            param.put("userId", user.getUserId());

            // TODO 以后从其他微服务中获取
//            FriendRelationship friendRelationship = friendRelationshipService.getFriendRelationship(param);
//            param.put("friendId", shiroService.getLoginUserBase().getId());
//            if (friendRelationship != null) {
//                resultMap.put("isFriend", true);
//            } else {
//                resultMap.put("isFriend", false);
//            }
            return ResultUtils.setParams(resultMap, ResultCode.CODE_200.code, ResultCode.CODE_200.msg);
        }
        return ResultUtils.setParams(resultMap, ResultCode.CODE_401.code, ResultCode.CODE_401.msg);
    }

    @Override
    @Transactional(timeout = 5000)
    public Map bindPhone(String phone, String code, String password) {
        logger.info("UserServiceImpl bindPhone begin...");
        Map resultMap = new HashMap();
        if (CheckNullUtils.checkParaNULL(phone, code)) {
            Map<String, Object> param = new HashMap<>();

            // TODO 以后使用微服务进行验证
//            if(configInfo.isSmsOpen()){
//
//                //获取短信验证码信息
//                param.put("account", phone);
//                SmsCode smsCode = smsCodeService.getSmsCode(param);
//
//                //如果短信验证码为空或者不等于用户提交的code，则提示验证码出错
//                if (smsCode == null || !code.equals(smsCode.getCode())) {
//                    return sendResult(ResultCode.CODE_802.code, ResultCode.CODE_802.msg, null);
//                }
//                //如果验证码已使用过，则提示重新获取
//                if (smsCode.getType() == SmsTemplateType.HAVE_USE) {
//                    return sendResult(ResultCode.CODE_803.code, ResultCode.CODE_803.msg, null);
//                }
//                //更新验证码为已使用
//                smsCode.setType(SmsTemplateType.HAVE_USE);
//                smsCodeService.updateSmsCode(smsCode);
//            }

            // 判断密码是否正确
            boolean right = userBaseService.checkOldPassword(shiroService.getLoginUserBase().getAccount(), password);
            if (!right) {
                return ResultUtils.setParams(resultMap, ResultCode.CODE_701.code, ResultCode.CODE_701.msg);
            }

            param.clear();
            param.put("phone", phone);
            User user = userDAO.selectOne(param);
            //如果该手机号没有被绑定，则可以继续绑定流程
            if (user == null) {
                user = userDAO.selectByUserId(shiroService.getLoginUserBase().getId());
                user.setPhone(phone);
                boolean result = userDAO.updateByPrimaryKeySelective(user) == 1;
                if (result) {
                    return ResultUtils.setParams(resultMap, ResultCode.CODE_200.code, ResultCode.CODE_200.msg);
                }
                return ResultUtils.setParams(resultMap, ResultCode.CODE_500.code, ResultCode.CODE_500.msg);
            }
            return ResultUtils.setParams(resultMap, ResultCode.CODE_718.code, ResultCode.CODE_718.msg);
        }
        return ResultUtils.setParams(resultMap, ResultCode.CODE_401.code, ResultCode.CODE_401.msg);

    }

    @Override
    public Map bindWeChat(String wechatId) {
        logger.info("UserServiceImpl bindWeChat begin...");
        Map resultMap = new HashMap();

        if (CheckNullUtils.checkParaNULL(wechatId)) {
            Map<String, Object> param = new HashMap<>();
            param.put("wechatId", wechatId);
            User user = userDAO.selectOne(param);
            //如果该微信号没有被绑定，则可以继续绑定流程
            if (user == null) {
                user = userDAO.selectByUserId(shiroService.getLoginUserBase().getId());
                user.setWechatId(wechatId);
                boolean result = userDAO.updateByPrimaryKeySelective(user) == 1;
                if (result) {
                    return ResultUtils.setParams(resultMap, ResultCode.CODE_200.code, ResultCode.CODE_200.msg);
                }
                return ResultUtils.setParams(resultMap, ResultCode.CODE_500.code, ResultCode.CODE_500.msg);
            }
            return ResultUtils.setParams(resultMap, ResultCode.CODE_717.code, ResultCode.CODE_717.msg);
        }
        return ResultUtils.setParams(resultMap, ResultCode.CODE_401.code, ResultCode.CODE_401.msg);
    }

    @Override
    public Map complainUser(String identify, Integer type, String content) {
        logger.info("UserServiceImpl complainUser begin...");

        Map resultMap = new HashMap();
        if (CheckNullUtils.checkParaNULL(identify, type)) {
            ComplainUser complainUser = new ComplainUser();

            Map<String, Object> param = new HashMap<>();
            param.put("identify", identify);
            User user = userDAO.selectOne(param);
            if (user != null) {
                complainUser.setUserId(user.getUserId());
            }
            complainUser.setIdentify(identify);
            complainUser.setType(type);
            complainUser.setContent(content);
            complainUser.setCreateTime(new Date());

            // TODO complainUserDAO.xml 没有完成
            boolean result = complainUserDAO.insertSelective(complainUser) == 1;
            if (result) {
                return ResultUtils.setParams(resultMap, ResultCode.CODE_200.code, ResultCode.CODE_200.msg);
            }
        }
        return ResultUtils.setParams(resultMap, ResultCode.CODE_401.code, ResultCode.CODE_401.msg);
    }

    @Override
    public Map shieldPhoneOn(String ifOn) {
        logger.info("UserServiceImpl shieldPhoneOn begin...");
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();

        Long userId = shiroService.getLoginUserBase().getId();
        param.put("userId", userId);
        UserSetting userSetting = userSettingDAO.selectOne(param);

        if (userSetting == null) {
            userSetting = new UserSetting();
            userSetting.setUserId(userId);
            userSetting.setNtyBySound(1);
            userSetting.setNtyByVibration(1);
            userSetting.setIsSpeakerOn(1);
            userSetting.setShowFriendsToStranger(1);
            userSetting.setShieldPhoneOn(Integer.valueOf(ifOn));
            if (!(userSettingDAO.insertSelective(userSetting) == 1)) {
                return ResultUtils.setParams(resultMap, ResultCode.CODE_500.code, ResultCode.CODE_500.msg);
            }
        } else {
            userSetting.setShieldPhoneOn(Integer.valueOf(ifOn));
            if (!(userSettingDAO.updateByPrimaryKeySelective(userSetting) == 1)) {
                return ResultUtils.setParams(resultMap, ResultCode.CODE_500.code, ResultCode.CODE_500.msg);
            }
        }
        return ResultUtils.setParams(resultMap, ResultCode.CODE_200.code, ResultCode.CODE_200.msg);
    }

    @Override
    public Map ifShieldPhoneOn() {
        logger.info("UserServiceImpl ifShieldPhoneOn begin...");
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        Long userId = shiroService.getLoginUserBase().getId();

        param.put("userId", userId);
        UserSetting userSetting = userSettingDAO.selectOne(param);
        if (null == userSetting) {
            // 配置为空时，默认可见
            resultMap.put("ifOn", 1);
        } else if (userSetting.getShieldPhoneOn() == 1) {
            resultMap.put("ifOn", 1);
        } else {
            resultMap.put("ifOn", 0);
        }
        return ResultUtils.setParams(resultMap, ResultCode.CODE_200.code, ResultCode.CODE_200.msg);
    }

    @Override
    public Map searchUserByPhones(String[] phones) {
        logger.info("UserServiceImpl searchUserByPhones begin...");
        Map resultMap = new HashMap();
        if (phones.length == 0) {
            return ResultUtils.setParams(resultMap, ResultCode.CODE_200.code, ResultCode.CODE_200.msg);
        }

        Map<String, Object> param = new HashMap<>();

        int totalNum = phones.length;
        int perNum = 10;
        int circleNum = totalNum / perNum;
        int remainNum = totalNum % perNum;

        List<String> phoneList = Arrays.asList(phones);
        List<User> userList = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < circleNum; i++) {
            param.clear();
            param.put("phones", phoneList.subList(index, index + perNum));
            param.put("filterShieldPhone", "true");
            userList.addAll(userDAO.selectParam(param));
            index = index + perNum;
        }

        if (remainNum > 0) {
            param.clear();
            param.put("phones", phoneList.subList(index, phoneList.size()));
            param.put("filterShieldPhone", "true");
            userList.addAll(userDAO.selectParam(param));
        }

        List<Map> resultMapList = new ArrayList<>();
        User currentUser = userDAO.selectByUserId(shiroService.getLoginUserBase().getId());
        for (User user : userList) {

            // 过滤被冻结的用户
            if (user.getUserBase().getFreeze() == 1) {
                continue;
            }

            // 过滤本机号码
            if (currentUser.getPhone().equals(user.getPhone())) {
                continue;
            }

            // TODO 以后调用 文件系统微服务
            //获取头像
//            ossFileUtils.getUserHead(user, null);

            Map<String, Object> map = new HashMap<>();
            map.put("phone", user.getPhone());

            // TODO user 转 userJson 不知道这里会不会有问题
            map.put("user", ConvertUtils.convertUser(user));
//            map.put("user", ConvertBeanUtils.convertDBModelToJSONModel(user));

            param.clear();
            //查询是否是好友
            param.put("userId", user.getUserId());

            // TODO　查询好友，通过其他微服务进行查询
//            param.put("friendId", shiroService.getLoginUserBase().getId());
//            FriendRelationship friendRelationship = friendRelationshipService.getFriendRelationship(param);
//            if (friendRelationship != null) {
//                map.put("isFriend", true);
//            } else {
//                map.put("isFriend", false);
//            }


            resultMapList.add(map);
        }
        resultMap.put("userList", resultMapList);
        return ResultUtils.setParams(resultMap, ResultCode.CODE_200.code, ResultCode.CODE_200.msg);
    }

    @Override
    public Map searchUserSuperLikeTimes() {
        logger.info("UserControllerImpl searchUserSuperLikeTimes begin...");

        int numOfSuperLike;
        List<UserSuperLike> userSuperLikes = userSuperLikeDAO.selectTodaySuperLike(shiroService.getLoginUserBase().getId());

        Map params = new HashMap();
        params.put("userId", shiroService.getLoginUserBase().getId());
        UserSuperLikeConfig userSuperLikeConfig = userSuperLikeConfigDAO.selectOne(params);

        if (null != userSuperLikeConfig) {
            numOfSuperLike = userSuperLikeConfig.getTimes();
        } else {

            // TODO 字典获取，在其他微服务中调用
//            numOfSuperLike = Integer.valueOf(sysDicService.getSysDicByKey(NUM_SUPER_LIKE).getValue());
            numOfSuperLike = 0;
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userSuperLike", userSuperLikes.size());
        resultMap.put("superLikeConfig", numOfSuperLike);

        return ResultUtils.setParams(resultMap, ResultCode.CODE_200.code, ResultCode.CODE_200.msg);

    }
}
