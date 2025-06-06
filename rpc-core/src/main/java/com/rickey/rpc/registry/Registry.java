package com.rickey.rpc.registry;

import com.rickey.rpc.config.RegistryConfig;
import com.rickey.rpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * @Description: 注册中心API接口
 * @Author: rickey-c
 * @Date: 2025/5/24 23:26
 */
public interface Registry {
    /**
     * 初始化
     *
     * @param registryConfig 服务配置
     */
    void init(RegistryConfig registryConfig);
    
    /**
     * 注册服务（服务端）
     * @param serviceMetaInfo 服务元信息
     * @throws Exception 异常
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     *
     * @param serviceMetaInfo 服务元信息
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     *
     * @param serviceKey 服务键名
     * @return 服务元信息
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();

    /**
     * 心跳检测
     */
    void heartBeat();

    /**
     * 监听（消费端）
     *
     * @param serviceNodeKey 监听节点
     */
    void watch(String serviceNodeKey);
}
