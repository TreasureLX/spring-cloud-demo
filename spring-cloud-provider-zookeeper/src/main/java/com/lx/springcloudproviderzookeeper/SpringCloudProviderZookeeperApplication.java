package com.lx.springcloudproviderzookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableHystrix
public class SpringCloudProviderZookeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudProviderZookeeperApplication.class, args);
    }

}
