package com.puputuan.dao;

import com.puputuan.basedao.BaseDAO;
import com.puputuan.model.UserSuperLike;

import java.util.List;
import java.util.Map;

/**
 * Created by chenzhuobin on 2017/6/2.
 */
public interface UserSuperLikeDAO extends BaseDAO<UserSuperLike, Long> {
    List<UserSuperLike> selectTodaySuperLike(Long userId);

    void deleteSuperLike(Map param);
}