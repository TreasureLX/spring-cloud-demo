package com.lx.springcloudconfig.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope  //适合用于开关、阈值和文案
public class EchoController {
    @Value("${my.name}")
    private String myName;

    @GetMapping("myName")
    public String getMyName(){
        return myName;
    }
}
