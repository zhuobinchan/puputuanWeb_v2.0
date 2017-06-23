package com.puputuan.utils;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

/**
 * Created by chenzhuobin on 2017/6/15.
 */
public class ConvertMapUtils {
    /**
     * map 转 multiValueMap
     * @param map
     * @param isRemoveNotString 该字段是否过滤不能转换为String的 object字段
     * @return
     */

    public static MultiValueMap mapToMultiValueMap(Map<String, Object> map, Boolean isRemoveNotString) {
        MultiValueMap multiValueMap = new LinkedMultiValueMap();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            String key = entry.getKey();

            if (isToString(value)) {
                multiValueMap.add(key, String.valueOf(value));
            } else if (value instanceof List) {

                for (Object listObject : (List) value) {
                    if (isToString(listObject)) {
                        multiValueMap.add(key, String.valueOf(listObject));
                    } else {
                        if (!isRemoveNotString) {
                            multiValueMap.add(key, listObject);
                        }
                    }
                }
            } else {
                if (!isRemoveNotString) {
                    multiValueMap.add(key, value);
                }

            }
        }
        return multiValueMap;
    }


    private static Boolean isToString(Object param) {
        if (param instanceof Integer || param instanceof String || param instanceof Double
                || param instanceof Float || param instanceof Long || param instanceof Boolean) {
            return true;
        }
        return false;
    }

}
