package com.puputuan.web.api.simple.impl;

import com.puputuan.common.enums.ResultCode;
import com.puputuan.common.mybatis.PageEntity;
import com.puputuan.common.mybatis.PagingResult;
import com.puputuan.controller.base.BaseController;
import com.puputuan.service.SimpleManageService;
import com.puputuan.utils.BeanMapUtils;
import com.puputuan.utils.ConvertUtils;
import com.puputuan.utils.MapRemoveNullUtils;
import com.puputuan.utils.ResultUtils;
import com.puputuan.web.api.simple.SimpleManageController;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenzhuobin on 2017/5/3.
 */
@RestController
public class SimpleManageControllerImpl extends BaseController implements SimpleManageController {
    @Resource
    private SimpleManageService simpleManageService;

    @Override
    public Object search(Integer methodType, Long id, String daoKey, String paramsJson, @ModelAttribute PageEntity pageEntity) {
        if (checkParaNULL(daoKey, methodType)) {

            Map<String, String> searchParams = new HashMap<>();
            if (checkParaNULL(paramsJson)) {
                searchParams = ConvertUtils.jsonToMap(paramsJson);
                MapRemoveNullUtils.removeNullEntry(searchParams);
            }
            Map<String, Object> resultMap = new HashMap<>();

            simpleManageService.initObjectDAO(BeanMapUtils.getDaoSimpleName(daoKey));
            switch (methodType) {
                case 1:
                    List<Object> list = simpleManageService.getObjectList(searchParams);
                    resultMap.put("list", list);
                    break;
                case 2:
                    if (checkParaNULL(id)) {
                        Object obj = simpleManageService.getObjectById(id);
                        resultMap.put("object", obj);
                    } else {
                        return sendResult(ResultCode.CODE_401.code, ResultCode.CODE_401.msg, null);
                    }
                    break;
                case 3:
                    pageEntity.setParam(searchParams);
                    PagingResult<Object> pagingForKeyword = simpleManageService.getObjectPage(pageEntity);
                    ResultUtils.setResultMap(resultMap, pagingForKeyword);
                    break;
                default:
                    break;
            }
            return sendResult(ResultCode.CODE_200.code, ResultCode.CODE_200.msg, resultMap);

        }
        return sendResult(ResultCode.CODE_401.code, ResultCode.CODE_401.msg, null);
    }

    @Override
    public Object complexSearch(Integer methodType, Long id,String daoKey, String methodKey, String paramsJson, @ModelAttribute PageEntity pageEntity) {
        if (checkParaNULL(daoKey, methodKey)) {
            Map<String, String> searchParams = new HashMap<>();
            if (checkParaNULL(paramsJson)) {
                searchParams = ConvertUtils.jsonToMap(paramsJson);
                MapRemoveNullUtils.removeNullEntry(searchParams);
            }

            Map<String, Object> resultMap = new HashMap<>();

            simpleManageService.initObjectDAO(BeanMapUtils.getDaoSimpleName(daoKey));
            switch (methodType) {
                case 1:
                    List<Object> list = simpleManageService.invokeSearchMethod(methodKey,searchParams);
                    resultMap.put("list", list);
                    break;
                case 2:
                    if (checkParaNULL(id)) {
                        Object obj = simpleManageService.getObjectById(id);
                        resultMap.put("object", obj);
                    } else {
                        return sendResult(ResultCode.CODE_401.code, ResultCode.CODE_401.msg, null);
                    }
                    break;
                case 3:
                    pageEntity.setParam(searchParams);
                    PagingResult<Object> pagingForKeyword = simpleManageService.invokeSearchMethodPage(methodKey,pageEntity);
                    ResultUtils.setResultMap(resultMap, pagingForKeyword);
                    break;
                default:
                    break;
            }
            return sendResult(ResultCode.CODE_200.code, ResultCode.CODE_200.msg, resultMap);

        }
        return sendResult(ResultCode.CODE_401.code, ResultCode.CODE_401.msg, null);
    }

    @Override
    public Object setStatus(String daoKey,Long[] ids, Integer status) {
        if (checkParaNULL(ids,status)){
            simpleManageService.initObjectDAO(BeanMapUtils.getDaoSimpleName(daoKey));
            for (Long id: ids) {
                simpleManageService.setObjectStatus(id,status);
            }
            return sendResult(ResultCode.CODE_200.code, ResultCode.CODE_200.msg, null);
        }
        return sendResult(ResultCode.CODE_401.code, ResultCode.CODE_401.msg, null);
    }


}
