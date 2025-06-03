package com.rickey.rpc.bootstrap;

import com.rickey.rpc.RpcApplication;

/**
 * @Description: 服务消费者启动类（初始化）
 * @Author: rickey-c
 * @Date: 2025/6/3 14:55
 */
public class ConsumerBootstrap {

    /**
     * 初始化
     */
    public static void init() {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
    }

}
