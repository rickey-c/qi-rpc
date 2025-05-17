package com.rickey.rpc.server;

/**
 * @Description: 服务接口
 * @Author: rickey-c
 * @Date: 2025/5/17 22:13
 */
public interface HttpServer {

    /**
     * 服务启动方法
     *
     * @param port 服务启动端口
     */
    void start(int port);
}
