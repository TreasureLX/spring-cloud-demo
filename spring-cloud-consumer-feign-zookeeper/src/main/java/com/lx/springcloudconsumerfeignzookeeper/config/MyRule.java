package com.lx.springcloudconsumerfeignzookeeper.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;

import java.util.List;

/**
 * @author lanxing
 */
public class MyRule extends AbstractLoadBalancerRule {


    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object key) {
        List<Server> list=getLoadBalancer().getAllServers();
        return list.get(0);
    }
}
