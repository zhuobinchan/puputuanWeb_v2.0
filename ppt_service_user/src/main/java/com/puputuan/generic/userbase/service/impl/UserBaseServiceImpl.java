package com.puputuan.generic.userbase.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.puputuan.common.mybatis.PageEntity;
import com.puputuan.common.mybatis.PagingResult;
import com.puputuan.generic.userbase.dao.UserBaseDAO;
import com.puputuan.generic.userbase.model.UserBase;
import com.puputuan.generic.userbase.service.UserBaseService;
import com.puputuan.model.User;
import com.puputuan.utils.PasswordHelper;
import com.puputuan.utils.UuidUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jian on 2015/6/17.
 */
@Service("userBaseService")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.INTERFACES)
public class UserBaseServiceImpl implements UserBaseService {


    @Resource(name = "userBaseDAO")
    private UserBaseDAO userBaseDAO;

    @Override
    public boolean add(UserBase object) throws Exception {
        if (object != null && object.getPassword() != null) {
            //密码加密
            object.setPassword(PasswordHelper.encryptPassword(object));
        }
        return userBaseDAO.insertSelective(object) > 0;
    }

    @Override
    public boolean remove(Long id) {
        return userBaseDAO.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean update(UserBase object) {
        if (object.getPassword() != null) {
            object.setPassword(PasswordHelper.encryptPassword(object));
        }
        return userBaseDAO.update(object) > 0;
    }

    @Override
    public boolean updateWithoutPassword(UserBase object) {
        //不更新密码
        return userBaseDAO.updateWithoutPassword(object) > 0;
    }

    @Override
    public boolean updatePassword(String account, String password) {
        //更新密前先加密
        UserBase userBase = new UserBase();
        userBase.setAccount(account);
        userBase.setPassword(password);
        userBase.setPassword(PasswordHelper.encryptPassword(userBase));
//        userBase.setPassword(new SimpleHash("md5", userBase.getPassword(), ByteSource.Util.bytes(userBase.getAccount()), 2).toHex());
        return userBaseDAO.updatePassword(userBase.getAccount(), userBase.getPassword()) > 0;
        //前端app使用md5加密后再发送密码,所以这里密码不需要再另外加密了
//        return userBaseDAO.updatePassword(account,password) > 0;
    }

    @Override
    public UserBase getById(Long id) {
        return userBaseDAO.selectByPrimaryKey(id);
    }

    @Override
    public UserBase getByAccount(String account) {
        Map<String,Object> params = new HashMap<>();
        params.put("account",account);
        return userBaseDAO.selectOne(params);
    }

    @Override
    public UserBase getByToken(String token) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        return userBaseDAO.selectOne(params);
    }

    @Override
    public List<UserBase> getAll() {
        return userBaseDAO.selectParam(new HashMap());
    }

    @Override
    public PagingResult<UserBase> getByPagination(PageEntity pageEntity) {

        PageHelper.startPage(pageEntity.getPage(), pageEntity.getSize());
        List<UserBase> list = userBaseDAO.selectParam(pageEntity.getParam());
        PageInfo<UserBase> pager = new PageInfo(list);
        return new PagingResult<>(pager.getPageNum(), pager.getTotal(), pager.getPages(), pager.getList());

    }

    @Override
    public List<UserBase> getByParam(Map<String, Object> params) {
        return userBaseDAO.selectParam(params);
    }

    @Override
    public boolean deleteUserBaseBatch(Long[] ids) {
        return userBaseDAO.deleteBatch(ids) > 0;
    }

    @Override
    public boolean freezeUserBaseBatch(List<Long> ids, boolean freeze) {
        return userBaseDAO.freezeBatch(ids, freeze, null) == ids.size();
    }

    @Override
    public boolean freezeUserBaseBatch(List<Long> ids, boolean freeze, String token) {
        return userBaseDAO.freezeBatch(ids, freeze, token) == ids.size();
    }

    @Override
    public boolean checkOldPassword(String account, String password) {
        UserBase userBase = new UserBase();
        userBase.setAccount(account);
        userBase.setPassword(password);
        //得到加密后的密码
        String oldPassword = PasswordHelper.encryptPassword(userBase);

        UserBase ub = userBaseDAO.selectByAccount(account);

        if (null != ub){
            if(ub.getPassword().equals(oldPassword)){
                return true;
            }
        }else{
            return false;
        }
        return false;
    }

    @Override
    public void updateUserBaseByUser(User user, Integer device) {
            user.getUserBase().setToken(UuidUtils.generateUuid());
            userBaseDAO.updateWithoutPassword(user.getUserBase());
            user.setDevice(device);
    }
}

