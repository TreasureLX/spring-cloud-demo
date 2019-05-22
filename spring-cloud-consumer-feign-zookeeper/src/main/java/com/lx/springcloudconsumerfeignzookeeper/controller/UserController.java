package com.lx.springcloudconsumerfeignzookeeper.controller;

import com.lx.springcloudzookeeperapi.entity.User;
import com.lx.springcloudzookeeperapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getUser")
    public User getUser(){
        return userService.getUser();
    }
}
