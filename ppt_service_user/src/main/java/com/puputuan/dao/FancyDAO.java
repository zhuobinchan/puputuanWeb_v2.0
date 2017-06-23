package com.puputuan.dao;

import com.puputuan.basedao.BaseDAO;
import com.puputuan.model.Fancy;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FancyDAO extends BaseDAO<Fancy, Long> {
    /**
     * 查询系统创建的还有用户自己的职业
     *
     * @param userId
     * @return
     */
    public List<Fancy> selectParamWithSystem(@Param("userId") Long userId);

    /**
     * 批量更新
     *
     * @param param
     * @return
     */
    public int updateBatch(Map param);
}