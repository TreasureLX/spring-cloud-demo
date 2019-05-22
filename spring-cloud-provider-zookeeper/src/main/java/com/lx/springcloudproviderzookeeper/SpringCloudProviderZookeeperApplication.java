package com.lx.springcloudproviderzookeeper;

import com.lx.springcloudzookeeperapi.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author lanxing
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringCloudProviderZookeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudProviderZookeeperApplication.class, args);
    }

}
