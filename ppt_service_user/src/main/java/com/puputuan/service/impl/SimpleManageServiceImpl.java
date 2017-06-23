package com.puputuan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.puputuan.common.enums.ExceptionMessage;
import com.puputuan.common.mybatis.PageEntity;
import com.puputuan.common.mybatis.PagingResult;
import com.puputuan.service.SimpleManageService;
import com.puputuan.utils.SpringContextUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by chenzhuobin on 2017/5/3.
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.INTERFACES)
public class SimpleManageServiceImpl implements SimpleManageService {
    private static final Logger logger = Logger.getLogger(SimpleManageServiceImpl.class);
    private Object daoObject = null;

    @Override
    public SimpleManageServiceImpl initObjectDAO(String daoName) {
        this.daoObject = SpringContextUtils.getBean(daoName);
        return this;
    }

    @Override
    public SimpleManageServiceImpl initObjectDAO(Class daoName) {
        this.daoObject = SpringContextUtils.getBean(daoName);
        return this;
    }

    @Override
    public Object getObjectById(Long id){
        try {
            Method method = daoObject.getClass().getMethod("selectByPrimaryKey",Serializable.class);
            return method.invoke(daoObject,id);
        } catch (NoSuchMethodException e) {
            logger.error(ExceptionMessage.NoSuchMethodException.getMessage(),e);
        } catch (IllegalAccessException e) {
            logger.error(ExceptionMessage.IllegalAccessException.getMessage(),e);
        } catch (InvocationTargetException e) {
            logger.error(ExceptionMessage.InvocationTargetException.getMessage(),e);
        }
        return null;
    }

    @Override
    public Object getObject(Map param) {
        try {
            Method method = daoObject.getClass().getMethod("selectOne",Map.class);
            return method.invoke(daoObject,param);
        } catch (NoSuchMethodException e) {
            logger.error(ExceptionMessage.NoSuchMethodException.getMessage(),e);
        } catch (IllegalAccessException e) {
            logger.error(ExceptionMessage.IllegalAccessException.getMessage(),e);
        } catch (InvocationTargetException e) {
            logger.error(ExceptionMessage.InvocationTargetException.getMessage(),e);
        }
        return null;
    }

    @Override
    public List<Object> getObjectList(Map param) {
        try {
            Method method = daoObject.getClass().getMethod("selectParam",Map.class);
            return (List<Object>) method.invoke(daoObject,param);
        } catch (NoSuchMethodException e) {
            logger.error(ExceptionMessage.NoSuchMethodException.getMessage(),e);
        } catch (IllegalAccessException e) {
            logger.error(ExceptionMessage.IllegalAccessException.getMessage(),e);
        } catch (InvocationTargetException e) {
            logger.error(ExceptionMessage.InvocationTargetException.getMessage(),e);
        }
        return Collections.emptyList();
    }

    @Override
    public PagingResult<Object> getObjectPage(PageEntity pageEntity) {
        PageHelper.startPage(pageEntity.getPage(), pageEntity.getSize());
        List<Object> list = getObjectList(pageEntity.getParam());
        PageInfo<Object> page = new PageInfo<>(list);
        return new PagingResult<>(page.getPageNum(), page.getTotal(), page.getPages(), page.getList());
    }

    @Override
    public boolean addObject(Object object) {
        try {
            Method method = daoObject.getClass().getMethod("insertSelective",Object.class);
            return (Integer)method.invoke(daoObject,object) == 1;
        } catch (NoSuchMethodException e) {
            logger.error(ExceptionMessage.NoSuchMethodException.getMessage(),e);
        } catch (IllegalAccessException e) {
            logger.error(ExceptionMessage.IllegalAccessException.getMessage(),e);
        } catch (InvocationTargetException e) {
            logger.error(ExceptionMessage.InvocationTargetException.getMessage(),e);
        }
        return false;
    }

    @Override
    public boolean updateObject(Object object) {
        try {
            Method method = daoObject.getClass().getMethod("updateByPrimaryKeySelective",Object.class);
            return (Integer)method.invoke(daoObject,object) == 1;
        } catch (NoSuchMethodException e) {
            logger.error(ExceptionMessage.NoSuchMethodException.getMessage(),e);
        } catch (IllegalAccessException e) {
            logger.error(ExceptionMessage.IllegalAccessException.getMessage(),e);
        } catch (InvocationTargetException e) {
            logger.error(ExceptionMessage.InvocationTargetException.getMessage(),e);
        }
        return false;
    }

    @Override
    public boolean removeObject(Long id) {
        try {

            Method method = daoObject.getClass().getMethod("deleteByPrimaryKey",Object.class);
            return (Integer)method.invoke(daoObject,id) == 1;
        } catch (NoSuchMethodException e) {
            logger.error(ExceptionMessage.NoSuchMethodException.getMessage(),e);
        } catch (IllegalAccessException e) {
            logger.error(ExceptionMessage.IllegalAccessException.getMessage(),e);
        } catch (InvocationTargetException e) {
            logger.error(ExceptionMessage.InvocationTargetException.getMessage(),e);
        }
        return false;
    }

    @Override
    public Object invokeMethodByName(String methodName, Class[] parameterTypes,Object... args) {
        try {
            Method method = daoObject.getClass().getMethod(methodName,parameterTypes);
            return method.invoke(daoObject,args);
        } catch (NoSuchMethodException e) {
            logger.error(ExceptionMessage.NoSuchMethodException.getMessage(),e);
        } catch (IllegalAccessException e) {
            logger.error(ExceptionMessage.IllegalAccessException.getMessage(),e);
        } catch (InvocationTargetException e) {
            logger.error(ExceptionMessage.InvocationTargetException.getMessage(),e);
        }
        return null;
    }

    @Override
    public List<Object> invokeSearchMethod(String methodName, Map searchMap) {
        try {
            Method method = daoObject.getClass().getMethod(methodName,Map.class);
            return (List<Object>) method.invoke(daoObject,searchMap);
        } catch (NoSuchMethodException e) {
            logger.error(ExceptionMessage.NoSuchMethodException.getMessage(),e);
        } catch (IllegalAccessException e) {
            logger.error(ExceptionMessage.IllegalAccessException.getMessage(),e);
        } catch (InvocationTargetException e) {
            logger.error(ExceptionMessage.InvocationTargetException.getMessage(),e);
        }
        return Collections.emptyList();
    }

    @Override
    public PagingResult<Object> invokeSearchMethodPage(String methodName, PageEntity pageEntity) {
        PageHelper.startPage(pageEntity.getPage(), pageEntity.getSize());
        List<Object> list = invokeSearchMethod(methodName, pageEntity.getParam());
        PageInfo<Object> page = new PageInfo<>(list);
        return new PagingResult<>(page.getPageNum(), page.getTotal(), page.getPages(), page.getList());
    }

    @Override
    public boolean setObjectStatus(Long id, Integer status) {
        try {
            Object object = getObjectById(id);
            Method method = object.getClass().getMethod("setStatus",Integer.class);
            method.invoke(object,status);
            return updateObject(object);
        }catch (NoSuchMethodException e) {
            logger.error(ExceptionMessage.NoSuchMethodException.getMessage(),e);
        } catch (IllegalAccessException e) {
            logger.error(ExceptionMessage.IllegalAccessException.getMessage(),e);
        } catch (InvocationTargetException e) {
            logger.error(ExceptionMessage.InvocationTargetException.getMessage(),e);
        }
        return false;
    }
}
