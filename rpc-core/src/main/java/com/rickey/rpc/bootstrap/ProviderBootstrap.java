package com.rickey.rpc.bootstrap;

import com.rickey.rpc.RpcApplication;
import com.rickey.rpc.config.RegistryConfig;
import com.rickey.rpc.config.RpcConfig;
import com.rickey.rpc.model.ServiceMetaInfo;
import com.rickey.rpc.model.ServiceRegisterInfo;
import com.rickey.rpc.registry.LocalRegistry;
import com.rickey.rpc.registry.Registry;
import com.rickey.rpc.registry.RegistryFactory;
import com.rickey.rpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * @Description: 服务消费者启动类（初始化）
 * @Author: rickey-c
 * @Date: 2025/6/3 15:01
 */
public class ProviderBootstrap {

    /**
     * 初始化
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 本地注册
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());

            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(rpcConfig.getVersion());
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }
        }

        // 启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }
}
