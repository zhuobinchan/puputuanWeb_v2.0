package com.puputuan.utils;

import java.util.Map;

/**
 * Created by chenzhuobin on 2017/6/1.
 */
public class CheckNullUtils {
    public static boolean checkParaNULL(Object... arg) {
        if (arg != null) {
            for (int i = 0; i < arg.length; i++) {
                if (null == arg[i] || "".equals(arg[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkMapNULL(Map... map) {
        if (map != null) {
            for (int i = 0; i < map.length; i++) {
                if (map[i] == null || map[i].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}
