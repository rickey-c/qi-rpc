package com.rickey.rpc.provider;


import com.rickey.rpc.RpcApplication;
import com.rickey.rpc.common.service.UserService;
import com.rickey.rpc.config.RegistryConfig;
import com.rickey.rpc.config.RpcConfig;
import com.rickey.rpc.model.ServiceMetaInfo;
import com.rickey.rpc.registry.LocalRegistry;
import com.rickey.rpc.registry.Registry;
import com.rickey.rpc.registry.RegistryFactory;
import com.rickey.rpc.server.HttpServer;
import com.rickey.rpc.server.VertexHttpServer;

/**
 * @Description: 生产者测试
 * @Author: rickey-c
 * @Date: 2025/5/25 18:10
 */
public class ProviderExample {

    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 web 服务
        HttpServer httpServer = new VertexHttpServer();
        httpServer.start(RpcApplication.getRpcConfig().getServerPort());
    }
}
