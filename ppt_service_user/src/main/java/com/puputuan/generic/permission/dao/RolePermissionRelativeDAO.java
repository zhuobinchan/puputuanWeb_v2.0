package com.puputuan.generic.permission.dao;

import com.puputuan.basedao.BaseDAO;
import com.puputuan.generic.permission.model.Permission;
import com.puputuan.generic.permission.model.Role;
import com.puputuan.generic.permission.model.RolePermissionRelative;

import java.util.List;
import java.util.Map;

/**
 * Created by jian on 2015/6/17.
 */
public interface RolePermissionRelativeDAO extends BaseDAO<RolePermissionRelative, Long> {

    /**
     * 查询所有包含该权限的角色
     * @param permission
     * @return
     */
    public List<Role> selectAllRoleContainPermission(Permission permission);

    /**
     * 查询该角色包含的所有权限
     * @param role
     * @return
     */
    public List<Permission> selectAllPermissionBelongRole(Role role);

    /**
     * 批量为角色添加权限
     * @param params
     * @return
     */
    public int insertPermissionToRole(Map params);

    /**
     * 批量删除角色权限
     * @param params
     * @return
     */
    public int deletePermissionFromRole(Map params);

}