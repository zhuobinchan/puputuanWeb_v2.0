package com.puputuan.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Created by chenzhuobin on 2017/5/31.
 */
@Configuration
public class FilterConfig {

    @Bean
    FilterRegistrationBean shiroFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("shiroFilter");
        DelegatingFilterProxy dfp = new DelegatingFilterProxy();
        dfp.setTargetBeanName("shiroFilter");
//        dfp.setBeanName("shiroFilter");
        registrationBean.setFilter(dfp);
        registrationBean.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        registrationBean.addInitParameter("targetFilterLifecycle", "true");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setEnabled(true);
        return registrationBean;
    }

}
