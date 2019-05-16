package com.lx.springcloudproviderzookeeper.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CircuitBreaker {

    /**
     * 超时时间
     * @return
     */
    long timeout() default 100;

    /**
     * 熔断方法
     * @return
     */
    String fallbackMethod();
}
