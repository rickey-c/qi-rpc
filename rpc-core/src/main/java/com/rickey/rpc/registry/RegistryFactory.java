package com.rickey.rpc.registry;

import com.rickey.rpc.spi.SpiLoader;

/**
 * @Description: 注册中心API接口
 * @Author: rickey-c
 * @Date: 2025/5/25 17:55
 */
public class RegistryFactory {

    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认注册中心
     */
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     * 获取实例
     *
     * @param key 注册中心key
     * @return 注册中心
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }

}
