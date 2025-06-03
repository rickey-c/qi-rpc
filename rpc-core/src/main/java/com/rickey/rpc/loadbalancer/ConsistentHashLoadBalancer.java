package com.rickey.rpc.loadbalancer;

import com.rickey.rpc.model.ServiceMetaInfo;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Description: 一致性hash负载均衡器
 * @Author: rickey-c
 * @Date: 2025/6/3 11:43
 */
public class ConsistentHashLoadBalancer implements LoadBalancer {

    /**
     * 一致性hash环，存放虚拟节点
     */
    private final TreeMap<Integer, ServiceMetaInfo> virtualNodes = new TreeMap<>();

    /**
     * 虚拟节点数量
     */
    private static final int VIRTUAL_NODE_NUM = 100;

    @Override
    @Nullable
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if (serviceMetaInfoList.isEmpty()) {
            return null;
        }
        // 构建虚拟机的hash环，每次进行选择的时候都进行构建，快速处理变化
        for (ServiceMetaInfo serviceMetaInfo : serviceMetaInfoList) {
            for (int i = 0; i < VIRTUAL_NODE_NUM; i++) {
                int hash = getHash(serviceMetaInfo.getServiceAddress() + "#" + i);
                virtualNodes.put(hash, serviceMetaInfo);
            }
        }

        // 获取请求的hash值
        int hash = getHash(requestParams);

        // 选择最接近，且大于等于请求hash值的虚拟节点
        Map.Entry<Integer, ServiceMetaInfo> entry = virtualNodes.ceilingEntry(hash);
        if (entry == null) {
            // 如果拿不到，就返回第一个节点
            entry = virtualNodes.firstEntry();
        }
        return entry.getValue();
    }

    /**
     * hash算法
     *
     * @param key hash的key
     * @return hashCode
     */
    private int getHash(Object key) {
        return key.hashCode();
    }
}
