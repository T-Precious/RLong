package com.rongzer.suzhou.scm.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitRequest {
    //限制时间 单位：毫秒
    long time() default 1000;
    //允许请求的次数
    int count() default 1;
}
