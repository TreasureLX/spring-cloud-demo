package com.lx.springcloudzookeeperapi.service;

import com.lx.springcloudzookeeperapi.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author lanxing
 */
@FeignClient("spring-cloud-provider-zookeeper")
public interface UserService {

    @RequestMapping(value = "getUser",method = RequestMethod.GET)
    User getUser();
}
