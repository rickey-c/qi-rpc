package com.rickey.rpc.config;

import com.rickey.rpc.registry.RegistryKeys;
import lombok.Data;

/**
 * @Description: 注册配置
 * @Author: rickey-c
 * @Date: 2025/5/24 23:20
 */
@Data
public class RegistryConfig {

    /**
     * 注册中心
     */
    private String registry = RegistryKeys.ETCD;

    /**
     * 注册中心地址
     */
    private String address = "http://localhost:2379";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 连接超时时间
     */
    private Long timeout = 10000L;

}
