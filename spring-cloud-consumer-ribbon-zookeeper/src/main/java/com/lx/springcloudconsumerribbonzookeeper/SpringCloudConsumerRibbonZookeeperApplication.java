package com.lx.springcloudconsumerribbonzookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author lanxing
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudConsumerRibbonZookeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsumerRibbonZookeeperApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate ribbonRestTemplate(){
        return new RestTemplate();
    }
}
