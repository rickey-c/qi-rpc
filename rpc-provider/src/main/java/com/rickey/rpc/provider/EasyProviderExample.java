package com.rickey.rpc.provider;

import com.rickey.rpc.common.service.UserService;
import com.rickey.rpc.registry.LocalRegistry;
import com.rickey.rpc.server.VertexHttpServer;

/**
 * @Description: 生产者测试
 * @Author: rickey-c
 * @Date: 2025/5/17 22:23
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        // 注册服务
        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);
        
        // 启动web服务
        VertexHttpServer server = new VertexHttpServer();
        server.start(8080);
    }
}
