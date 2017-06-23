package com.puputuan.generic.permission.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.puputuan.generic.permission.service.ShiroService;
import com.puputuan.generic.userbase.model.UserBase;
import com.puputuan.generic.userbase.service.UserBaseService;
import com.puputuan.service.MemcacheService;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenzhuobin on 2017/5/23.
 */
public class SysUserFilter extends PathMatchingFilter {
//    @Resource(name = "userBaseService")
//    private UserBaseService userBaseService;
    @Resource
    private MemcacheService memcacheService;

    @Resource(name = "shiroService")
    private ShiroService shiroService;

    //protected static final Logger LOG = LoggerFactory.getLogger(SysUserFilter.class);


    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String token = ((HttpServletRequest) request).getHeader("Authorization");
        //System.out.println("token: " + token);
        UserBase user = null;
        if (token != null) {
//            user = userBaseService.getByToken(token);
            //session 同步 从memcache中获取
            user = memcacheService.getUserBaseByToken(token);
        }
        if (user != null) {
            shiroService.setLoginUserBase(user);
            return true;
        } else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Map<String, Object> params = new HashMap<>();
            params.put("code", 402);
            params.put("message", "访问用户的token错误");
            response.getWriter().append(JSON.toJSONString(params));
            return false;
        }
    }
}