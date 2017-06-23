package com.puputuan.dao;

import com.puputuan.basedao.BaseDAO;
import com.puputuan.model.Job;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface JobDAO extends BaseDAO<Job, Long> {


    /**
     * 批量更新
     *
     * @param param
     * @return
     */
    public int updateBatch(Map param);

    /**
     * 查询系统创建的还有用户自己的职业
     *
     * @param userId
     * @return
     */
    public List<Job> selectParamWithSystem(@Param("userId") Long userId);
}