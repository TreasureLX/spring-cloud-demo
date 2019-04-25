package com.lx.springcloudconsumercustom.controller;

import com.lx.springcloudconsumercustom.annotation.CustomLoadBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Configuration
public class ServiceController {

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 依赖注入自定义
     */
    @Autowired
    @CustomLoadBalance
    private RestTemplate restTemplate;


    @Autowired
    @LoadBalanced
    private RestTemplate ribbonRestTemplate;

    @GetMapping("invoke/{serviceName}/hello")
    public String invokeHello(@PathVariable String serviceName, @RequestParam String msg) {
        return restTemplate.getForObject(serviceName + "/hello?msg=" + msg, String.class);
    }

    @GetMapping("invoke/ribbon/{serviceName}/hello")
    public String ribbonInvokeHello(@PathVariable String serviceName, @RequestParam String msg) {
        return ribbonRestTemplate.getForObject(serviceName + "/hello?msg=" + msg, String.class);
    }




//    private static Set<String> targetUrls = new ConcurrentSkipListSet<>();
//
//    private static String currentServiceName = "spring-cloud-provider-zookeeper";

//    private static Map<String, Set<String>> services = new ConcurrentHashMap<>();
//
//    //更新所有的缓存
//    @Scheduled(fixedRate = 10 * 1000)
//    private void updateServices() {
//        Map<String, Set<String>> oldService = services;
//        Map<String, Set<String>> newService = new ConcurrentHashMap<>();
//        discoveryClient.getServices().forEach(serviceName -> {
//            List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
//            Set<String> newTartUrls = instances.stream()
//                    .map(s -> s.isSecure() ?
//                            "https://" + s.getHost() + ":" + s.getPort() :
//                            "http://" + s.getHost() + ":" + s.getPort()
//                    )
//                    .collect(Collectors.toSet());
//            newService.put(serviceName, newTartUrls);
//        });
//        services = newService;
//        oldService.clear();
//        System.out.println(services);
//    }

    /**
     * 每10秒更新服务缓存
     */
//    @Scheduled(fixedRate = 10 * 1000)
//    private void updateTargetUrls() {
//        System.out.println("更新服务缓存");
//        List<ServiceInstance> instances = discoveryClient.getInstances(currentServiceName);
//        Set<String> newTartUrls = instances.stream()
//                .map(s -> s.isSecure() ?
//                        "https://" + s.getHost() + ":" + s.getPort() :
//                        "http://" + s.getHost() + ":" + s.getPort()
//                )
//                .collect(Collectors.toSet());
//        //保存旧的服务列表
//        Set<String> oldTargetUrls = targetUrls;
//        //更新服务列表
//        targetUrls = newTartUrls;
//        //清除服务
//        oldTargetUrls.clear();
//    }

//    @GetMapping("invoke/hello")
//    public String invokeHello(@RequestParam String msg) {
//        List<String> currentTargetUrls = new ArrayList<>(targetUrls);
//        int size = currentTargetUrls.size();
//        int next = new Random().nextInt(size);
//        String url = currentTargetUrls.get(next);
//        return restTemplate.getForObject(url + "/hello?msg=" + msg, String.class);
//    }

//    @GetMapping("invoke/{serviceName}/hello")
//    public String invokeHello(@PathVariable String serviceName, @RequestParam String msg) {
//        List<String> currentTargetUrls = new ArrayList<>(services.get(serviceName));
//        int size = currentTargetUrls.size();
//        int next = new Random().nextInt(size);
//        String url = currentTargetUrls.get(next);
//        return restTemplate.getForObject(url + "/hello?msg=" + msg, String.class);
//    }



}
