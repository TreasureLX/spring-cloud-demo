package com.lx.springcloudproviderzookeeper.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("getAllService")
    public List<String> getAllService() {
        return discoveryClient.getServices();
    }

    @HystrixCommand(
            fallbackMethod = "errorContent",
            commandProperties = {
                    @HystrixProperty(name = "",value = "00")
            }
    )
    @GetMapping("hello")
    public String hello(@RequestParam String msg) {
        System.out.println(msg);
        return "hello "+msg;
    }

    public String errorContent(){
        return "fail";
    }
}
