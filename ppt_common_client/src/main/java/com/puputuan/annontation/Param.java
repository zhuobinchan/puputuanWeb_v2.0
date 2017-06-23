package com.puputuan.annontation;

import java.lang.annotation.*;

/**
 * Created by chenzhuobin on 2017/6/13.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Param {
    String value() default "";
}
