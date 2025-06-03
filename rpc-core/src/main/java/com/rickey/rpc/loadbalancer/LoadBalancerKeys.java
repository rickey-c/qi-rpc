package com.rickey.rpc.loadbalancer;

/**
 * @Description: 负载均衡器常量名
 * @Author: rickey-c
 * @Date: 2025/6/3 14:12
 */
public interface LoadBalancerKeys {

    /**
     * 轮询
     */
    String ROUND_ROBIN = "roundRobin";

    /**
     * 随机
     */
    String RANDOM = "random";

    /**
     * 哈希环
     */
    String CONSISTENT_HASH = "consistentHash";
}
