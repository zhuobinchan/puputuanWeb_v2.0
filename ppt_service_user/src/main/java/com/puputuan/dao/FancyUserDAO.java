package com.puputuan.dao;

import com.puputuan.basedao.BaseDAO;
import com.puputuan.model.FancyUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FancyUserDAO extends BaseDAO<FancyUser, Long> {
    public int deleteByUserId(@Param("userId") Long userId);
}