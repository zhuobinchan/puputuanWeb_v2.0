package com.puputuan.web.api.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by jian on 16/3/15.
 */
@RequestMapping("/ctrl/common")
public interface CommonController {
    //================================== 其他 ======================================

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    ModelAndView login();

    /**
     * 管理员登录
     *
     * @param account
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    ModelAndView login(String account, String password);


    /**
     * 管理员登出
     *
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    ModelAndView logout();


    @RequestMapping(value = "/checkLogin",method = RequestMethod.GET)
    @ResponseBody
    Object checkLogin(String token);
}
