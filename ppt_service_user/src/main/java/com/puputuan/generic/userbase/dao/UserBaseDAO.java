package com.puputuan.generic.userbase.dao;

import com.puputuan.basedao.BaseDAO;
import com.puputuan.generic.userbase.model.UserBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by jian on 2015/6/17.
 */
public interface UserBaseDAO extends BaseDAO<UserBase, Long> {

    public UserBase selectByAccount(@Param("account") String account);

    public int updatePassword(@Param("account") String account, @Param("password") String password);

    public int updateWithoutPassword(UserBase userBase);

    public int deleteBatch(@Param("ids") Long[] ids);

    int freezeBatch(@Param("ids") List<Long> ids, @Param("freeze") boolean freeze, @Param("token") String token);

    public int update(UserBase userBase);


}