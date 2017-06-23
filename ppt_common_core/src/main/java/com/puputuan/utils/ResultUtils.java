package com.puputuan.utils;



import com.puputuan.common.mybatis.PagingResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guodikai on 2016/11/1.
 */
public class ResultUtils {

    public static Map setResultMap(Map resultMap, PagingResult pagingResult){
        if(null != pagingResult){
            resultMap.put("list", pagingResult.getResultList());
            resultMap.put("totalPage", pagingResult.getTotalPage());
            resultMap.put("totalSize", pagingResult.getTotalSize());
            resultMap.put("currentPage", pagingResult.getCurrentPage());
        }
        return resultMap;
    }

    public static Map setParams(Map params, int code, String message){
        if (params == null){
            params = new HashMap<>();
        }
        params.put("code", code);
        params.put("message", message);
        return params;
    }

}
