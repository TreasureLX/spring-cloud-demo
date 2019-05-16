package com.lx.springcloudproviderzookeeper.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SemaphoreCircuitBreaker {
    /**
     * 超时时间
     * @return
     */
    int value() default 10;

    /**
     * 熔断方法
     * @return
     */
    String fallbackMethod();
}
