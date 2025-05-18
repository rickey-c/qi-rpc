package com.rickey.rpc.config;

import lombok.Data;

/**
 * @Description: RPC配置
 * @Author: rickey-c
 * @Date: 2025/5/18 23:30
 */
@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "qi-rpc";

    /**
     * 版本
     */
    private String version = "v1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

}
