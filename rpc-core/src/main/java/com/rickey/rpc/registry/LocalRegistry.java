package com.rickey.rpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 本地注册服务器-demo用
 * @Author: rickey-c
 * @Date: 2025/5/17 22:26
 */
public class LocalRegistry {

    /**
     * 注册信息存储
     */
    private static final Map<String,Class<?>> map = new ConcurrentHashMap<>();

    /**
     * 向本地注册服务
     * @param serviceName 服务名
     * @param implClass 实现类
     */
    public static void register(String serviceName,Class<?> implClass){
        map.put(serviceName,implClass);
    }

    /**
     * 获取服务
     * @param serviceName 服务名
     * @return
     */
    public static Class<?> get(String serviceName){
        return map.get(serviceName);
    }

    /**
     * 删除服务 
     * @param serviceName 服务名
     */
    public static void remove(String serviceName){
        map.remove(serviceName);
    }
}
