package com.puputuan.service;



import com.puputuan.common.mybatis.PageEntity;
import com.puputuan.common.mybatis.PagingResult;
import com.puputuan.service.impl.SimpleManageServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by chenzhuobin on 2017/5/3.
 */
public interface SimpleManageService {

    /**
     * 初始化Dao信息，需要调用的Dao信息
     *
     * @param daoName
     */
    SimpleManageServiceImpl initObjectDAO(String daoName);

    SimpleManageServiceImpl initObjectDAO(Class daoName);
    /**
     * 基础方法，通过id获取 对象
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    Object getObjectById(Long id);

    /**
     * 基础方法，通过筛选参数 获取单个对象
     * @param param
     * @return
     */
    @Transactional(readOnly = true)
    Object getObject(Map param);

    /**
     *基础方法， 通过筛选参数 获取对象列表 不分页
      * @param param
     * @return
     */
    @Transactional(readOnly = true)
    List<Object> getObjectList(Map param);

    /**
     *通过筛选参数 获取对象列表 分页
     * @param pageEntity
     * @return
     */
    @Transactional(readOnly = true)
    PagingResult<Object> getObjectPage(PageEntity pageEntity);

    /**
     *基础方法，添加对象
     * @param object
     * @return
     */
    @Transactional
    boolean addObject(Object object);

    /**
     *基础方法，更新对象
     * @param object
     * @return
     */
    @Transactional
    boolean updateObject(Object object);

    /**
     *基础方法，删除对象
     * @param id
     * @return
     */
    @Transactional
    boolean removeObject(Long id);

    /**
     *动态调用方法
     * @param methodName 方法名
     * @param parameterTypes 方法参数类型
     * @param args 要调用的参数
     * @return
     */
    @Transactional
    Object invokeMethodByName(String methodName, Class[] parameterTypes, Object... args);

    /**
     *动态调用，特殊的查询方法
     * @param methodName 方法名
     * @param searchMap 筛选的参数
     * @return
     */
    @Transactional(readOnly = true)
    List<Object> invokeSearchMethod(String methodName, Map searchMap);

    /**
     *动态调用，特殊的查询方法  分页
     * @param methodName 方法名
     * @param pageEntity 分页工具类
     * @return
     */
    @Transactional(readOnly = true)
    PagingResult<Object> invokeSearchMethodPage(String methodName, PageEntity pageEntity);

    /**
     *设置对象状态，简单的更新对象
     * @param id
     * @param status
     * @return
     */
    @Transactional
    boolean setObjectStatus(Long id, Integer status);
}
