package com.puputuan;

import com.puputuan.binding.RestfulProxyFactory;
import com.puputuan.restful.UserAppRestfulService;
import com.puputuan.restful.UserRestfulService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guodikai on 2017/6/2.
 */
@Controller
@SpringBootApplication
public class Application extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {



    @RequestMapping("/aa")
    @ResponseBody
    String home() {
        UserAppRestfulService proxy2 = (UserAppRestfulService) RestfulProxyFactory.newInstance(UserAppRestfulService.class);
        Map<String,Object> param = new HashMap();
        param.put("level","+1");

        Map<String, String> head = new HashMap<>();
        head.put("Authorization", "84baff70109147a3832f39b3c2639c46");

        System.out.println(proxy2.getInfo(head,param));
        return "app_info";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(9081);
    }
}
