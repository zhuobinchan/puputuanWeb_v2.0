package com.puputuan.generic.permission.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.puputuan.common.mybatis.PageEntity;
import com.puputuan.common.mybatis.PagingResult;
import com.puputuan.generic.permission.dao.RolePermissionRelativeDAO;
import com.puputuan.generic.permission.model.Permission;
import com.puputuan.generic.permission.model.Role;
import com.puputuan.generic.permission.model.RolePermissionRelative;
import com.puputuan.generic.permission.service.RolePermissionRelativeService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jian on 2015/6/17.
 */
@Service("rolePermissionRelativeService")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.INTERFACES)
public class RolePermissionRelativeServiceImpl implements RolePermissionRelativeService {


    @Resource(name = "rolePermissionRelativeDAO")
    private RolePermissionRelativeDAO rolePermissionRelativeDAO;

    @Override
    public boolean add(RolePermissionRelative object) {
        return rolePermissionRelativeDAO.insertSelective(object) > 0;
    }

    @Override
    public boolean remove(Long id) {
        return rolePermissionRelativeDAO.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean update(RolePermissionRelative object) {
        return rolePermissionRelativeDAO.updateByPrimaryKeySelective(object) > 0;
    }

    @Override
    public RolePermissionRelative getById(Long id) {
        return rolePermissionRelativeDAO.selectByPrimaryKey(id);
    }

    @Override
    public List<RolePermissionRelative> getAll() {
        return rolePermissionRelativeDAO.selectParam(new HashMap());
    }

    @Override
    public PagingResult<RolePermissionRelative> getByPagination(PageEntity pageEntity) {

        PageHelper.startPage(pageEntity.getPage(), pageEntity.getSize());
        List<RolePermissionRelative> list = rolePermissionRelativeDAO.selectParam(pageEntity.getParam());
        PageInfo<RolePermissionRelative> pager = new PageInfo(list);
        return new PagingResult<>(pager.getPageNum(), pager.getTotal(), pager.getPages(), pager.getList());

    }

    @Override
    public List<RolePermissionRelative> getByParam(Map<String, Object> params) {
        return rolePermissionRelativeDAO.selectParam(params);
    }


    @Override
    public List<Role> getAllRoleContainPermission(Permission permission) {
        return rolePermissionRelativeDAO.selectAllRoleContainPermission(permission);
    }

    @Override
    public List<Permission> getAllPermissionBelongRole(Role role) {
        return rolePermissionRelativeDAO.selectAllPermissionBelongRole(role);
    }

    @Override
    public List<Permission> getAllPermissionByRoleId(Long roleId) {
        Role tempRole = new Role();
        tempRole.setId(roleId);
        return getAllPermissionBelongRole(tempRole);
    }

    @Override
    public PagingResult<Permission> getAllPermissionByRoleIdPagination(Long roleId, PageEntity pageEntity) {
        PageHelper.startPage(pageEntity.getPage(), pageEntity.getSize());
        List<Permission> list = getAllPermissionByRoleId(roleId);
        PageInfo<Permission> pager = new PageInfo(list);
        return new PagingResult<>(pager.getPageNum(), pager.getTotal(), pager.getPages(), pager.getList());
    }

    @Override
    public boolean addPermissionToRole(Long[] permissionIds, Long roleId) {
        Map<String,Object> params = new HashMap<>();
        params.put("list",permissionIds);
        params.put("roleId",roleId);
        return rolePermissionRelativeDAO.insertPermissionToRole(params) > 0;
    }

    @Override
    public boolean removePermissionFromRole(Long[] permissionIds, Long roleId) {
        Map<String,Object> params = new HashMap<>();
        params.put("list",permissionIds);
        params.put("roleId",roleId);
        return rolePermissionRelativeDAO.deletePermissionFromRole(params) > 0;
    }
}

