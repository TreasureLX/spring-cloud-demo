package com.lx.springcloudconfig.server.config;

import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RestController
public class ConfigSeverConfig {

    private String value = "lanxing";

    @GetMapping("changeValue")
    public String changeValue(String value) {
        this.value = value;
        return value;
    }

    @Bean
    public EnvironmentRepository environmentRepository() {
        return new EnvironmentRepository() {
            @Override
            public Environment findOne(String application, String profile, String label) {
                Environment environment = new Environment("lx", profile, "master", null, null);
                List<PropertySource> list = environment.getPropertySources();
                Map<String, Object> source = new HashMap<String, Object>();
                source.put("my.name", value);
                PropertySource propertySource = new PropertySource("map", source);
                list.add(propertySource);
                return environment;
            }
        };
    }
}

