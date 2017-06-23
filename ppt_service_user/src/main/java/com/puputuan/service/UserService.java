package com.puputuan.service;

import com.puputuan.generic.userbase.model.UserBase;
import com.puputuan.model.User;
import com.puputuan.model.jsonMedol.Authorization;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Map;

/**
 * Created by chenzhuobin on 2017/5/26.
 */
public interface UserService {

//    User getUserById(Long id);
//
//    User getUserByIdentifyId(String identifyId);
//
//    User getUser(Map param);
//
//    User getUserByParam(Map param);
//
//    List<User> getUserList(Map param);
//
//    PagingResult<User> getUserPage(PageEntity pageEntity);
//
//    boolean addUser(User object);
//
//    boolean updateUser(User object);
//
//    boolean updateUserByUserId(User object);
//
//    boolean removeUser(Long id);
//
//    /**
//     * 根据用户userId查询用户
//     *
//     * @param userId
//     * @return
//     */
//    public User getByUserId(Long userId);
//
//
//    /**
//     * 根据用户userId查询用户，并且带有兴趣职业中还包含了自己没有选择但是是属于系统的兴趣职业     *
//     *
//     * @param userId
//     * @return
//     */
//    public User getByUserIdWithSystem(Long userId);
//
//    /**
//     * 根据用户的identity或是昵称查询用户
//     *
//     * @param param
//     * @return
//     */
//    public List<User> getByIdentifyNameList(Map param);
//
//    /**
//     * 根据用户的identity或是昵称查询用户，分页
//     *
//     * @param pageEntity
//     * @return
//     */
//    public PagingResult<User> getByIdentifyNamePage(PageEntity pageEntity);
//
//    /**
//     * 根据密码登录
//     * @param account
//     * @param password
//     * @return 返回result Map
//     */

    //注册信息
    Map register(User user, UserBase userBase, String code, CommonsMultipartFile file);

    Map resetPassword(String account, String password, String code);

    Map getInfo();

    Map updateInfo(User user, Long avatarId, String[] jobStrings, String[] interestStrings, String[] fancyStrings);

    Map updatePassword(String oldPassword, String password);

    Map searchByIdentify(String identify);

    Map bindPhone(String phone, String code, String password);

    Map bindWeChat(String wechatId);

    Map complainUser(String identify, Integer type, String content);

    Map shieldPhoneOn(String ifOn);

    Map ifShieldPhoneOn();

    Map searchUserByPhones(String[] phones);

    Map searchUserSuperLikeTimes();


    /**
     *开放这个函数方便调用
     * @param user
     */
    void setUserInterestJobFancy(User user);

    /**
     *开放这个函数方便调用
     * @param user
     * @return
     */
    Authorization getAuthorizationFromUser(User user);
}
