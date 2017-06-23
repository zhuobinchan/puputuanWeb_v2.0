package com.puputuan.annontation;

import java.lang.annotation.*;

/**
 * Created by chenzhuobin on 2017/6/14.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Header {
    String value() default "";
}
