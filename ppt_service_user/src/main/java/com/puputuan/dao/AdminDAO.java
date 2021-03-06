package com.puputuan.dao;

import com.puputuan.basedao.BaseDAO;
import com.puputuan.model.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDAO extends BaseDAO<Admin, Long> {


    /**
     * 根据用户id查询管理员信息
     *
     * @param userId
     * @return
     */
    public Admin selectByUserId(@Param("userId") Long userId);

}