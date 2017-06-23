package com.puputuan.controller.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;


import com.puputuan.common.enums.ResultCode;
import com.puputuan.utils.BeanUtil;
import com.puputuan.utils.CheckNullUtils;
import com.puputuan.utils.EmojiCharacterUtil;
import com.puputuan.utils.JSONPropertyFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mac on 14/12/10.
 */
public abstract class BaseController {

    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     *
     * @param resultMap 该map需要携带code 和 message 两个字段
     * @param sendToken
     * @param str
     * @return
     */

    protected Object sendResult(Map<String, Object> resultMap, boolean sendToken, String... str){
        if (checkMapNULL(resultMap)&&checkParaNULL(resultMap.get("code"),resultMap.get("message"))){
            List<String> strings = new ArrayList<>(Arrays.asList(str));
            if (!sendToken) {
                strings.add("token");
                strings.add("password");
            }
            com.puputuan.common.utils.JSONPropertyFilter filter = new com.puputuan.common.utils.JSONPropertyFilter(strings);
            return JSON.parse(EmojiCharacterUtil.emojiRecovery2(JSON.toJSONString(resultMap, filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat)));
        }
        return sendResult(ResultCode.CODE_500.code,ResultCode.CODE_500.msg,null);
    }

    protected Object sendResult(Map<String, Object> resultMap, String... str){
        return sendResult(resultMap,false,str);
    }


    /**
     * 发送回复给客户端
     *
     * @param params
     */
    protected Object sendResult(int code, String message, Map<String, Object> params, String... str) {
        return sendResult(code, message, params, false, str);
    }

    /**
     * 发送回复给客户端
     *
     * @param params
     */
    protected Object sendResult(int code, String message, Map<String, Object> params, boolean sendToken, String... str) {
        if (params == null)
            params = new HashMap<>();
        params.put("code", code);
        params.put("message", message);
        List<String> strings = new ArrayList<>(Arrays.asList(str));
        if (!sendToken) {
            strings.add("token");
            strings.add("password");
        }
        JSONPropertyFilter filter = new JSONPropertyFilter(strings);
        return JSON.parse(EmojiCharacterUtil.emojiRecovery2(JSON.toJSONString(params, filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat)));
    }

    /**
     * 发送回复给客户端
     *
     * @param params
     */
    protected Object sendResult(int code, String message, Map<String, Object> params, Map<Class, String[]> filters) {
        if (params == null)
            params = new HashMap<>();
        params.put("code", code);
        params.put("message", message);
        String paramsJSON = JSON.toJSONString(params);
        for (Map.Entry<Class, String[]> entry : filters.entrySet()) {
            JSONPropertyFilter filter = new JSONPropertyFilter(entry.getKey(), entry.getValue());
            paramsJSON = JSON.toJSONString(paramsJSON, filter, SerializerFeature.WriteMapNullValue);
        }
        return JSON.parse(paramsJSON);
    }

    /**
     * 发送回复给Web客户端
     *
     * @param params
     */
    protected ModelAndView sendResult(int code, String view, String message, Map<String, Object> params) {
        if (params == null)
            params = new HashMap<>();
        ModelAndView model = new ModelAndView();
        params.put("code", code);
        params.put("message", message);
        model.addAllObjects(params);
        model.setViewName(view);
        return model;
    }

    /**
     * 发送回复给Web客户端
     *
     * @param params
     */
    protected ModelAndView sendResult(String view, Map<String, Object> params) {
        if (params == null)
            params = new HashMap<>();
        ModelAndView model = new ModelAndView();
        model.addAllObjects(params);
        model.setViewName(view);
        return model;
    }

    /**
     * 非空参数检测 当存在非空参数为空时返回 401
     *
     * @param arg
     */
    protected boolean checkParaNULL(Object... arg) {
        return CheckNullUtils.checkParaNULL(arg);
    }

    protected boolean checkMapNULL(Map... map){
        return CheckNullUtils.checkMapNULL(map);
    }

    /**
     * pojo对象转换为map集合
     *
     * @param object
     * @return
     */
    public static Map<String, Object> pojoToMap(Object object) {
        return BeanUtil.bean2Map(object);
    }
}
