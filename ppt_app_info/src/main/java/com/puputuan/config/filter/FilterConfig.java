package com.puputuan.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chenzhuobin on 2017/6/16.
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean loginFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new LoginFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }
}
