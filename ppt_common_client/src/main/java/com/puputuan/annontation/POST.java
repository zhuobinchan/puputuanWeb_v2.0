package com.puputuan.annontation;

import java.lang.annotation.*;

/**
 * Created by chenzhuobin on 2017/6/13.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@HttpMethod("POST")
@Documented
public @interface POST {
}
