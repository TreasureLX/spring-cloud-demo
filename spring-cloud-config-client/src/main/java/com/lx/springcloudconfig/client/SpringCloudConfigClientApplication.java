package com.lx.springcloudconfig.client;

import com.lx.springcloudconfig.client.healthinfo.MyHealthIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Set;

/**
 * @author lanxing
 */
@SpringBootApplication
@EnableScheduling
public class SpringCloudConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigClientApplication.class, args);
    }

    @Bean
    public MyHealthIndicator myHealthIndicator() {
        return new MyHealthIndicator();
    }

    private ContextRefresher contextRefresher;

    @Autowired
    public SpringCloudConfigClientApplication(ContextRefresher contextRefresher) {
        this.contextRefresher = contextRefresher;
    }

    /**
     * 自定义刷新容器
     */
    @Scheduled(fixedRate = 5 * 1000,initialDelay = 3*1000)
    public void autoRefresh() {
        Set<String> newProperty = contextRefresher.refresh();
        System.out.println("update property ==>" + newProperty);
    }


}
