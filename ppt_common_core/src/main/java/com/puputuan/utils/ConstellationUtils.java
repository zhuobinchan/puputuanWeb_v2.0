package com.puputuan.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by chenzhuobin on 2017/6/2.
 */
public class ConstellationUtils {
    public static String getConstellation(Date birthday) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthday);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String[] starArr = {"魔羯座", "水瓶座", "双鱼座", "白羊座",
                "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座"};

        int[] beginDay = {22, 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22};  // 两个星座分割日--开始
        int[] endDay = {19, 18, 20, 20, 20, 21, 22, 22, 22, 22, 21, 21};  // 两个星座分割日--结束

        // 所查询日期在分割日之后，索引+1，否则不变
        if (day > endDay[month]) {
            month = month + 1;
        }
        //当超过12月份的，则回到1月份
        if (month >= 0 && month <= 11) {
            return starArr[month];
        }
        // 返回索引指向的星座string
        return starArr[0];
    }

}
