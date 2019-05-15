package com.lx.springcloudproviderzookeeper.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author lanxing
 */
@RestController
public class CustomController {

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


    private void before() {
        System.out.println("before");
    }

    private void after() {
        System.out.println("after");
    }

    public String errorContent() {
        return "fault";
    }
}
