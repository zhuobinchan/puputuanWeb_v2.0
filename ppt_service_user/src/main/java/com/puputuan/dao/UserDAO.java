package com.puputuan.dao;

import com.puputuan.basedao.BaseDAO;
import com.puputuan.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by chenzhuobin on 2017/6/1.
 */
@Repository
public interface UserDAO extends BaseDAO<User,Long> {

    /**
     * 根据用户id更新信息
     *
     * @param user
     * @return
     */
    public int updateByUserId(User user);

    /**
     * 根据用户userId查询用户
     *
     * @param userId
     * @return
     */
    public User selectByUserId(@Param("userId") Long userId);

    /**
     * 根据param查询用户
     *
     * @param param
     * @return
     */
    public User selectByParam(Map param);

    /**
     * 根据用户userId查询用户，并且带有兴趣职业中还包含了自己没有选择但是是属于系统的兴趣职业
     *
     * @param userId
     * @return
     */
    public User selectByUserIdWithSystem(@Param("userId") Long userId);

    /**
     * 根据用户的identity或是昵称查询用户
     *
     * @param param
     * @return
     */
    public List<User> selectByIdentifyName(Map param);
}
