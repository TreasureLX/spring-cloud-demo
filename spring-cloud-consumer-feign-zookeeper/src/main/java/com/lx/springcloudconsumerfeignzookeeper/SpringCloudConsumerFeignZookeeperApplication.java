package com.lx.springcloudconsumerfeignzookeeper;

import com.lx.springcloudconsumerfeignzookeeper.config.MyRule;
import com.lx.springcloudzookeeperapi.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author lanxing
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = UserService.class)
@RibbonClient(configuration = SpringCloudConsumerFeignZookeeperApplication.class,
        name = "spring-cloud-provider-zookeeper")
public class SpringCloudConsumerFeignZookeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsumerFeignZookeeperApplication.class, args);
    }

    @Bean
    public MyRule myRule(){
        return new MyRule();
    }
}
