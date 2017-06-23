package com.puputuan.generic.permission.dao;

import com.puputuan.basedao.BaseDAO;
import com.puputuan.generic.permission.model.Role;

/**
 * Created by jian on 2015/6/17.
 */
public interface RoleDAO extends BaseDAO<Role, Long> {

    public Role selectByRole(String role);
}