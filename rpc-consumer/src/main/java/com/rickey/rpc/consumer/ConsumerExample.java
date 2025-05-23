package com.rickey.rpc.consumer;

import com.rickey.rpc.common.model.User;
import com.rickey.rpc.common.service.UserService;
import com.rickey.rpc.config.RpcConfig;
import com.rickey.rpc.proxy.ServiceProxyFactory;
import com.rickey.rpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 消费者示例
 * @Author: rickey-c
 * @Date: 2025/5/18 23:46
 */
@Slf4j
public class ConsumerExample {
    public static void main(String[] args) {
        // 加载配置
        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        log.info("Loaded RPC Config: {}", rpcConfig);

        // 获取代理对象
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);

        // 构造请求参数
        User user = new User();
        user.setName("rickey");

        // 调用远程服务
        User newUser = userService.getUser(user);
        if (newUser != null) {
            log.info("User name: {}", newUser.getName());
        } else {
            log.warn("user == null");
        }

        // 调用获取数字的方法
        short number = userService.getNumber();
        log.info("Number: {}", number);
    }
}