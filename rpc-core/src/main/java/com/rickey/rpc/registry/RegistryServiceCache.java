package com.rickey.rpc.registry;


import com.rickey.rpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * @Description: 注册中心本地缓存
 * @Author: rickey-c
 * @Date: 2025/5/26 00:30
 */
public class RegistryServiceCache {

    /**
     * 服务缓存
     */
    List<ServiceMetaInfo> serviceCache;

    /**
     * 写缓存
     *
     * @param newServiceCache 新缓存
     */
    void writeCache(List<ServiceMetaInfo> newServiceCache) {
        this.serviceCache = newServiceCache;
    }

    /**
     * 读缓存
     *
     * @return 服务元信息列表
     */
    List<ServiceMetaInfo> readCache() {
        return this.serviceCache;
    }

    /**
     * 清空缓存
     */
    void clearCache() {
        this.serviceCache = null;
    }
}
