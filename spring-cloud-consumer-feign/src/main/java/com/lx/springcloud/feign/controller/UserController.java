package com.lx.springcloud.feign.controller;

import com.lx.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/test")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getString")
    public String getString(){
        return userService.echo("Hello");
    }
}
