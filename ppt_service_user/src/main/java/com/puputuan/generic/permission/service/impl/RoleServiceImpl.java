package com.puputuan.generic.permission.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.puputuan.common.mybatis.PageEntity;
import com.puputuan.common.mybatis.PagingResult;
import com.puputuan.generic.permission.dao.RoleDAO;
import com.puputuan.generic.permission.model.Role;
import com.puputuan.generic.permission.service.RoleService;
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
@Service("roleService")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.INTERFACES)
public class RoleServiceImpl implements RoleService {


    @Resource(name = "roleDAO")
    private RoleDAO roleDAO;

    @Override
    public boolean add(Role object) {
        return roleDAO.insertSelective(object) > 0;
    }

    @Override
    public boolean remove(Long id) {
        return roleDAO.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean update(Role object) {
        return roleDAO.updateByPrimaryKeySelective(object) > 0;
    }

    @Override
    public Role getById(Long id) {
        return roleDAO.selectByPrimaryKey(id);
    }

    @Override
    public Role getByRole(String role) {
        return roleDAO.selectByRole(role);
    }

    @Override
    public List<Role> getAll() {
        return roleDAO.selectParam(new HashMap());
    }

    @Override
    public PagingResult<Role> getByPagination(PageEntity pageEntity) {

        PageHelper.startPage(pageEntity.getPage(), pageEntity.getSize());
        List<Role> list = roleDAO.selectParam(pageEntity.getParam());
        PageInfo<Role> pager = new PageInfo(list);
        return new PagingResult<>(pager.getPageNum(), pager.getTotal(), pager.getPages(), pager.getList());

    }

    @Override
    public List<Role> getByParam(Map<String, Object> params) {
        return roleDAO.selectParam(params);
    }
}

