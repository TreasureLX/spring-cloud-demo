package com.lx.springcloudproviderzookeeper.controller;

import com.lx.springcloudproviderzookeeper.annotation.SemaphoreCircuitBreaker;
import com.lx.springcloudproviderzookeeper.annotation.TimeoutCircuitBreaker;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author lanxing
 */
@RestController
public class CustomController {
    private static final Log logger = LogFactory.getLog(SpringApplication.class);

    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private static final Random random = new Random();
    private static final int maxTime = 100;

    /**
     * 简单版，流程繁琐，不能停止代码运行
     *
     * @param msg
     * @return
     * @throws InterruptedException
     */
    @GetMapping("way1")
    public String way1(String msg) throws InterruptedException {
        int time = random.nextInt(200);
        long startTime = System.currentTimeMillis();
        before();
        Thread.sleep(time);
        after();
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        if (costTime > maxTime) {
            return errorContent();
        }
        return msg;
    }

    /**
     * 通过Future实现容错
     *
     * @param msg
     * @return
     */
    @GetMapping("way2")
    public String way2(String msg) {
        try {
            Future<String> future = executorService.submit(() -> {
                int time = random.nextInt(200);
                before();
                Thread.sleep(time);
                after();
                return msg;
            });
            String result = future.get(maxTime, TimeUnit.MILLISECONDS);
            return result;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return errorContent();
        }
    }

    /**
     * 通过全局拦截器来实现熔断处理
     *
     * @param msg
     * @return
     * @throws Exception
     */
    @GetMapping("way3")
    public String way3(String msg) throws Exception {
        Future<String> future = executorService.submit(() -> {
            int time = random.nextInt(200);
            before();
            Thread.sleep(time);
            after();
            return msg;
        });
        String result = null;
        try {
            result = future.get(maxTime, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            throw e;
        }
        return result;
    }

    /**
     * 通过注解的方式来实现低耦合的熔断
     *
     * @param msg
     * @return
     * @throws Exception
     */
    @GetMapping("way4")
    public String way4(String msg) throws Exception {
        int time = random.nextInt(200);
        before();
        Thread.sleep(time);
        after();
        return msg;
    }

    /**
     * 通过AOP和注解高级实现
     * @param msg
     * @return
     * @throws Exception
     */
    @TimeoutCircuitBreaker(fallbackMethod = "errorContent",timeout = 100)
    @GetMapping("way5")
    public String way5(String msg) throws Exception {
        int time = random.nextInt(200);
        before();
        Thread.sleep(time);
        after();
        return msg;
    }

    @SemaphoreCircuitBreaker(fallbackMethod = "errorContent",value = 2)
    @GetMapping("way6")
    public String way6(String msg) throws Exception {
        before();
//        Thread.sleep(15000000);
        after();
        return msg;
    }

    private void before() {
        logger.info("before");
    }

    private void after() {
        logger.info("after");
    }

    public String errorContent() {
        return "fault";
    }
}
