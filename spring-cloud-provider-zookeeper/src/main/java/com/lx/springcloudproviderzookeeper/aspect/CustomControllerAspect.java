package com.lx.springcloudproviderzookeeper.aspect;

import com.lx.springcloudproviderzookeeper.annotation.CircuitBreaker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.lang.reflect.Method;
import java.util.concurrent.*;

/**
 * @author lanxing
 */
@Aspect
@Component
public class CustomControllerAspect {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Around("execution(* com.lx.springcloudproviderzookeeper.controller.*.*(..))")
    public Object timeOutWay(ProceedingJoinPoint joinPoint) throws Throwable {
        CircuitBreaker circuitBreaker = null;
        //1.获取切点方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        circuitBreaker = method.getAnnotation(CircuitBreaker.class);
        //2.获取切点参数
        Object[] args = joinPoint.getArgs();
        Object returnValue = null;
        //3.加了熔断注解
        if (circuitBreaker != null) {
            long timeout = circuitBreaker.timeout();
            Future<Object> future = executorService.submit(() -> {
                Object obj = new Object();
                try {
                    if (args.length > 0) {
                        obj = joinPoint.proceed(args);
                    } else {
                        obj = joinPoint.proceed();
                    }
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                return obj;
            });
            try {
                returnValue = future.get(timeout, TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                returnValue = errorContent();
            }
            //4.没加熔断注解
        } else {
            if (args.length > 0) {
                returnValue = joinPoint.proceed(args);
            } else {
                returnValue = joinPoint.proceed();
            }
        }
        return returnValue;
    }

    public String errorContent() {
        return "fault";
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }
}
