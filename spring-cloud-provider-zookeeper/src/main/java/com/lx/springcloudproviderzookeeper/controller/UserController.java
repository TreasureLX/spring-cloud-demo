package com.lx.springcloudproviderzookeeper.controller;

import com.lx.springcloudzookeeperapi.entity.User;
import com.lx.springcloudzookeeperapi.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserService {

    @GetMapping("getUser")
    @Override
    public User getUser() {
        User user=new User();
        user.setName("lanxing");
        user.setAge(24);
        return user;
    }
}
