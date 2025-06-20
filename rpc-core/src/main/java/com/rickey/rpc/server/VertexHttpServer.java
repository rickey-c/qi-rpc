package com.rickey.rpc.server;

import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 使用Vert.x实现服务接口
 * @Author: rickey-c
 * @Date: 2025/5/17 22:13
 */
@Slf4j
public class VertexHttpServer implements HttpServer {

    @Override
    public void doStart(int port) {
        // 创建vertx实例
        Vertx vertx = Vertx.vertx();

        // 创建Http服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 监听端口并处理请求
        server.requestHandler(new HttpServerHandler());

        // 启动 HTTP 服务器，并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                log.info("HTTP Server started successfully and is now listening on port: {}", port);
            } else {
                log.error("HTTP Server failed to doStart on port: {}. Cause: {}", port, result.cause().getMessage(), result.cause());
            }
        });
    }

}
