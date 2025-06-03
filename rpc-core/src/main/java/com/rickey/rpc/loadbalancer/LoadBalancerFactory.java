package com.rickey.rpc.loadbalancer;

import com.rickey.rpc.spi.SpiLoader;

/**
 * @Description: 负载均衡器工厂
 * @Author: rickey-c
 * @Date: 2025/6/3 14:14
 */
public class LoadBalancerFactory {

    static {
        SpiLoader.load(LoadBalancer.class);
    }

    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    /**
     * 获取实例
     *
     * @param key 负载均衡器名字
     * @return 负载均衡器
     */
    public static LoadBalancer getInstance(String key) {
        return SpiLoader.getInstance(LoadBalancer.class, key);
    }
}
