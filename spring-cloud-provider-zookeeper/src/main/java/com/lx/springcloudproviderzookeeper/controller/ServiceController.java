package com.lx.springcloudproviderzookeeper.controller;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * @author lanxing
 */
@RestController
public class ServiceController {

    @Autowired
    private DiscoveryClient discoveryClient;

    private static final Random random = new Random();

    @GetMapping("getAllService")
    public List<String> getAllService() {
        return discoveryClient.getServices();
    }

    @HystrixCommand(
            fallbackMethod = "errorContent",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
            }
    )
    @GetMapping("hello")
    public String hello(@RequestParam String msg) throws InterruptedException {
        int time = random.nextInt(200);
        System.out.println("before");
        Thread.sleep(time);
        System.out.println("after");
        return "hello " + msg;
    }

    public String errorContent(String message) {
        return "fault";
    }

    @GetMapping("test")
    public String test(@RequestParam String msg) throws InterruptedException {
        return new MyHystrixCommand().execute();
    }


    private static class MyHystrixCommand extends com.netflix.hystrix.HystrixCommand<String> {

        public MyHystrixCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("test"),100);
        }


        @Override
        protected String run() throws Exception {
            int time = random.nextInt(200);
            System.out.println("before");
            Thread.sleep(time);
            System.out.println("after");
            return "hello world";
        }

        @Override
        protected String getFallback() {
            return "fault";
        }
    }


}
