package com.lx.springcloudproviderzookeeper.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * @author lanxing
 */
@Aspect
@Component
public class CustomControllerAspect {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Around("execution(* com.lx.springcloudproviderzookeeper.controller.CustomController.way4(..)) && args(msg)")
    public Object timeOutWay(ProceedingJoinPoint joinPoint, String msg) throws Throwable {
        Future<Object> future = executorService.submit(() -> {
            Object obj = new Object();
            try {
                obj = joinPoint.proceed(new Object[]{msg});
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return obj;
        });
        Object returnValue = null;

        try {
            returnValue = future.get(100, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            returnValue = errorContent();
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
