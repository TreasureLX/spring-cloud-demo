package com.lx.springcloudproviderzookeeper.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

//    @Autowired
//    private DiscoveryClient discoveryClient;
//
//    @GetMapping("getAllService")
//    public List<String> getAllService() {
//        return discoveryClient.getServices();
//    }

//    @HystrixCommand(
//            fallbackMethod = "errorContent",
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
//            }
//    )
    @GetMapping("hello")
    public String hello(@RequestParam String msg) {
        System.out.println(msg);
        return "hello "+msg;
    }

    public String errorContent(String message){
        return "fail";
    }
}
