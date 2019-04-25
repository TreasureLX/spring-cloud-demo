package com.lx.springcloudconsumercustom.config;

import com.lx.springcloudconsumercustom.annotation.CustomLoadBalance;
import com.lx.springcloudconsumercustom.loadBalance.LoadBalanceRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;

@Configuration
public class Config {

    @Bean
    public ClientHttpRequestInterceptor interceptor() {
        return new LoadBalanceRequestInterceptor();
    }
    /**
     * 自定义RestTemplate
     * @return
     */
    @Bean
    @CustomLoadBalance
    public RestTemplate restTemplate() {
        RestTemplate restTemplate=new RestTemplate();
        return restTemplate;
    }

    @Bean
    @LoadBalanced
    public RestTemplate ribbonRestTemplate(){
        return new RestTemplate();
    }


    @Bean
    @Autowired
    public Object customer(@CustomLoadBalance Collection<RestTemplate> restTemplates,
                           ClientHttpRequestInterceptor interceptor){
        restTemplates.forEach(
                r-> r.setInterceptors(Arrays.asList(interceptor))
        );
        return new Object();
    }

}
