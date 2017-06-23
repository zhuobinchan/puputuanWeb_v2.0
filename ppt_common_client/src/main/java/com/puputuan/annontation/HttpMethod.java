package com.puputuan.annontation;

import java.lang.annotation.*;

/**
 * Created by chenzhuobin on 2017/6/13.
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpMethod {
    String GET = "GET";
    String POST = "POST";
    String PUT = "PUT";
    String DELETE = "DELETE";
    String HEAD = "HEAD";
    String OPTIONS = "OPTIONS";

    String value();
}
