package com.puputuan.dao;

import com.puputuan.basedao.BaseDAO;
import com.puputuan.model.AdminLoginHis;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AdminLoginHisDAO extends BaseDAO<AdminLoginHis, Long> {

    List<AdminLoginHis> selectAdminLoginHisByParam(Map param);

    int deleteBatch(Map param);
}