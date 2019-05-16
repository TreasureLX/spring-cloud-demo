package com.lx.springcloudproviderzookeeper.aspect;

import com.lx.springcloudproviderzookeeper.annotation.TimeoutCircuitBreaker;
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
        TimeoutCircuitBreaker circuitBreaker = null;
        //1.获取切点方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //获取切点注解
        circuitBreaker = method.getAnnotation(TimeoutCircuitBreaker.class);
        //3.获取切点参数
        Object[] args = joinPoint.getArgs();
        Object returnValue = null;
        //4.获取切点对象
        Object target=joinPoint.getTarget();
        //5.根据是否加熔断注解来进行逻辑操作
        if (circuitBreaker != null) {
            long timeout = circuitBreaker.timeout();
            //6.获取容错方法
            String fallbackMethodName=circuitBreaker.fallbackMethod();
            Method fallbackMethod= method.getDeclaringClass().getMethod(fallbackMethodName);
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
                future.cancel(true);
                returnValue = fallbackMethod.invoke(target);
            }
        } else {
            if (args.length > 0) {
                returnValue = joinPoint.proceed(args);
            } else {
                returnValue = joinPoint.proceed();
            }
        }
        return returnValue;
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }
}
