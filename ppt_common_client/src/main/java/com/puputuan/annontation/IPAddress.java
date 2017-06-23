package com.puputuan.annontation;

import org.springframework.context.annotation.Bean;

import java.lang.annotation.*;

/**
 * Created by chenzhuobin on 2017/6/13.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IPAddress {
    String ip() default "";

    int port() default 8080;

}
