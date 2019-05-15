package com.lx.springcloudproviderzookeeper.config;

import com.lx.springcloudproviderzookeeper.interceptor.CircuitBreakerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lanxing
 */
@Configuration
public class WebMcvConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CircuitBreakerInterceptor());
    }
}
