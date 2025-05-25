package com.rickey.rpc;

import com.rickey.rpc.config.RegistryConfig;
import com.rickey.rpc.config.RpcConfig;
import com.rickey.rpc.constant.RpcConstant;
import com.rickey.rpc.registry.Registry;
import com.rickey.rpc.registry.RegistryFactory;
import com.rickey.rpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: RPC框架应用类
 * @Author: rickey-c
 * @Date: 2025/5/18 23:38
 */
@Slf4j
public class RpcApplication {

    /**
     * 初始化配置
     */
    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化，支持传入自定义配置
     *
     * @param newRpcConfig rpc配置信息
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init,config = {}", rpcConfig.toString());
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init,config = {}", registryConfig);
    }

    /**
     * 初始化
     */
    public static void init() {
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            // 配置加载失败，使用默认值
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }


    /**
     * 获取配置
     *
     * @return RPC配置
     */
    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
