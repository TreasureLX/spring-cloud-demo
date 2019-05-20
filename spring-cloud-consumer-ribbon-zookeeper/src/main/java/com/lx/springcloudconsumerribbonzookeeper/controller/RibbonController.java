package com.lx.springcloudconsumerribbonzookeeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author lanxing
 */
@RestController
public class RibbonController {

    @Autowired
    @LoadBalanced
    private RestTemplate ribbonRestTemplate;

    @GetMapping("get/{msg}")
    public String ribbonInvokeHello(@PathVariable String msg) {
        return ribbonRestTemplate.getForObject( "http://spring-cloud-provider-zookeeper/way4?msg=" + msg, String.class);
    }

}
