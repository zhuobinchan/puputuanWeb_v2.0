package com.puputuan.info;

import com.puputuan.CoreTest;
import com.puputuan.SmsTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by guodikai on 2017/6/2.
 */
@Controller
@EnableAutoConfiguration
public class HelloController {
    @RequestMapping("/hello")
    @ResponseBody
    String home() {
        new CoreTest().testCore();
        new SmsTest().testSms();
        return "Hello ,spring boot!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HelloController.class, args);
        //运行之后在浏览器中访问：http://localhost:8080/hello
    }
}
