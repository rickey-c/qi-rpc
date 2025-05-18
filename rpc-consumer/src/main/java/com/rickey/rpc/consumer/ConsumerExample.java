package com.rickey.rpc.consumer;

import com.rickey.rpc.config.RpcConfig;
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
        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        log.info("Loaded RPC Config: {}", rpcConfig);
    }
}
