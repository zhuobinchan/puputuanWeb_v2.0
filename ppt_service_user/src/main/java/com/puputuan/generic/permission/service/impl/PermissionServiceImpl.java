package com.puputuan.generic.permission.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.puputuan.common.mybatis.PageEntity;
import com.puputuan.common.mybatis.PagingResult;
import com.puputuan.generic.permission.dao.PermissionDAO;
import com.puputuan.generic.permission.model.Permission;
import com.puputuan.generic.permission.service.PermissionService;
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
@Service("permissionService")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.INTERFACES)
public class PermissionServiceImpl implements PermissionService {


    @Resource(name = "permissionDAO")
    private PermissionDAO permissionDAO;

    @Override
    public boolean add(Permission object) {
        return permissionDAO.insertSelective(object) > 0;
    }

    @Override
    public boolean remove(Long id) {
        return permissionDAO.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean update(Permission object) {
        return permissionDAO.updateByPrimaryKeySelective(object) > 0;
    }

    @Override
    public Permission getById(Long id) {
        return permissionDAO.selectByPrimaryKey(id);
    }

    @Override
    public List<Permission> getAll() {
        return permissionDAO.selectParam(new HashMap());
    }

    @Override
    public PagingResult<Permission> getByPagination(PageEntity pageEntity) {

        PageHelper.startPage(pageEntity.getPage(), pageEntity.getSize());
        List<Permission> list = permissionDAO.selectParam(pageEntity.getParam());
        PageInfo<Permission> pager = new PageInfo(list);
        return new PagingResult<>(pager.getPageNum(), pager.getTotal(), pager.getPages(), pager.getList());

    }

    @Override
    public List<Permission> getByParam(Map<String, Object> params) {
        return permissionDAO.selectParam(params);
    }
}

