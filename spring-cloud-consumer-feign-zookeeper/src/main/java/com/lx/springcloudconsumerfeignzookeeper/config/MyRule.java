package com.lx.springcloudconsumerfeignzookeeper.config;

import com.netflix.loadbalancer.*;

import java.util.List;

/**
 * @author lanxing
 */
public class MyRule extends AbstractLoadBalancer {



    @Override
    public Server chooseServer(Object key) {
        return null;
    }


    @Override
    public List<Server> getServerList(ServerGroup serverGroup) {
        return null;
    }

    @Override
    public LoadBalancerStats getLoadBalancerStats() {
        return null;
    }
}
