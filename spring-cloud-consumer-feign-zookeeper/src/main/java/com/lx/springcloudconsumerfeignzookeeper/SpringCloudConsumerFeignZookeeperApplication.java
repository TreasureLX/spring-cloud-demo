package com.lx.springcloudconsumerfeignzookeeper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author lanxing
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudConsumerFeignZookeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsumerFeignZookeeperApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RestController
    public class TestController {

        private final RestTemplate restTemplate;

        @Autowired
        public TestController(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

        /**
         * 获取所有的服务
         * @return
         */
        @RequestMapping(value = "/getAllService", method = RequestMethod.GET)
        public List<String> getAllService() {
            return restTemplate.getForObject("http://spring-cloud-provider-zookeeper/getAllService/" , List.class);
        }
    }

}
