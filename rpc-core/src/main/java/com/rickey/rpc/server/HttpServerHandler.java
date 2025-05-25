package com.rickey.rpc.server;

import com.rickey.rpc.RpcApplication;
import com.rickey.rpc.registry.LocalRegistry;
import com.rickey.rpc.request.RpcRequest;
import com.rickey.rpc.response.RpcResponse;
import com.rickey.rpc.serializer.Serializer;
import com.rickey.rpc.serializer.SerializerFactory;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Description: http请求处理类
 * @Author: rickey-c
 * @Date: 2025/5/17 22:38
 */
@Slf4j
public class HttpServerHandler implements Handler<HttpServerRequest> {

    /**
     * 处理请求
     *
     * @param request 请求
     */
    @Override
    public void handle(HttpServerRequest request) {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        log.info("Received request : {},uri : {}", request.method(), request.uri());

        request.bodyHandler(body -> {
            // 获取请求
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                // 1.反序列化请求未对象，并从请求中获取参数
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // 构造响应对象
            RpcResponse rpcResponse = new RpcResponse();
            // 非空检查
            if (Objects.isNull(rpcRequest)) {
                rpcResponse.setMessage("request is null!");
                doResponse(request, rpcResponse, serializer);
                return;
            }

            try {
                // 2.根据服务名，从注册中心获取对应的服务实现类
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                // 3.通过反射调用方法
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
                // 4.封装响应结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            } catch (Exception e) {
                log.error("Error occurred while invoking method: {}, with parameters: {}, error: {}",
                        rpcRequest.getMethodName(), rpcRequest.getArgs(), e.getMessage(), e);
                rpcResponse.setException(e);
                rpcResponse.setMessage(e.getMessage());
            }
            doResponse(request, rpcResponse, serializer);
        });
    }

    /**
     * 响应
     *
     * @param request    请求
     * @param response   响应
     * @param serializer 序列化器
     */
    private void doResponse(HttpServerRequest request, RpcResponse response, Serializer serializer) {
        HttpServerResponse httpServerResponse = request.response()
                .putHeader("content-type", "application/json");
        try {
            byte[] serialized = serializer.serialize(response);
            httpServerResponse.end(Buffer.buffer(serialized));
        } catch (IOException e) {
            log.error("IOException happen");
            throw new RuntimeException(e);
        }

    }
}

