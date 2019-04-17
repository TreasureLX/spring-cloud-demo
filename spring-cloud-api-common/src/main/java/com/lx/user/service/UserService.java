package com.lx.user.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("spring-cloud-provider")
public interface UserService {

    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    String echo(@PathVariable("string") String str);

}
