package com.puputuan;

import com.alibaba.fastjson.JSON;
import com.puputuan.common.mybatis.PageEntity;
import com.puputuan.dao.AdminLoginHisDAO;
import com.puputuan.service.MemcacheService;
import com.puputuan.service.SimpleManageService;
import com.puputuan.utils.MemcacheUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Created by chenzhuobin on 2017/5/25.
 */
@Controller
@SpringBootApplication
@MapperScan(basePackages = "com.puputuan.**.dao")
public class Application {

    @Resource
    private SimpleManageService simpleManageService;

    @Resource
    private MemcacheUtils memcacheUtils;

    @RequestMapping("/")
    @ResponseBody
    String home() {
//        PageEntity pageEntity = new PageEntity();
//        pageEntity.setPage(1);
//        pageEntity.setSize(15);
//        pageEntity.setParam(new HashMap());
//        return JSON.toJSONString(simpleManageService.initObjectDAO(AdminLoginHisDAO.class).invokeSearchMethodPage("selectAdminLoginHisByParam",pageEntity));
        memcacheUtils.add("aaaa",1212);
        memcacheUtils.cas("aaaa",13155);
        return  JSON.toJSONString(memcacheUtils.get("aaaa"));
    }

    @RequestMapping("/abc")
    @ResponseBody
    String home2() {
        PageEntity pageEntity = new PageEntity();
        pageEntity.setPage(2);
        pageEntity.setSize(15);
        pageEntity.setParam(new HashMap());
        return JSON.toJSONString(simpleManageService.initObjectDAO(AdminLoginHisDAO.class).invokeSearchMethodPage("selectAdminLoginHisByParam",pageEntity));
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}