package com.puputuan.config.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.puputuan.binding.RestfulProxyFactory;
import com.puputuan.restful.CommonRestfulService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenzhuobin on 2017/6/16.
 */
public class LoginFilter implements Filter {

    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("Authorization");
        CommonRestfulService commonRestfulService = (CommonRestfulService) RestfulProxyFactory.newInstance(CommonRestfulService.class);
        Map param = new HashMap();
        param.put("token",token);
        String jsonString = commonRestfulService.checkLogin(param);

        JSONObject jsonObject = JSON.parseObject(jsonString);

        if ((Integer)jsonObject.get("code")!=200){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(jsonString);
        }else {
            filterChain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
