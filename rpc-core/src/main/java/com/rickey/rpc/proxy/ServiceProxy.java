package com.rickey.rpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.rickey.rpc.RpcApplication;
import com.rickey.rpc.request.RpcRequest;
import com.rickey.rpc.response.RpcResponse;
import com.rickey.rpc.serializer.Serializer;
import com.rickey.rpc.serializer.SerializerFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description: 动态代理类(JDK动态代理)
 * @Author: rickey-c
 * @Date: 2025/5/17 23:03
 */
@Slf4j
public class ServiceProxy implements InvocationHandler {

    /**
     * 服务地址，这里先写死
     */
    private static final String SERVICE_URL = "http://localhost:8080";

    /**
     * 序列化器
     */
    final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());


    /**
     * @param proxy  the proxy instance that the method was invoked on
     * @param method the {@code Method} instance corresponding to
     *               the interface method invoked on the proxy instance.  The declaring
     *               class of the {@code Method} object will be the interface that
     *               the method was declared in, which may be a superinterface of the
     *               proxy interface that the proxy class inherits the method through.
     * @param args   an array of objects containing the values of the
     *               arguments passed in the method invocation on the proxy instance,
     *               or {@code null} if interface method takes no arguments.
     *               Arguments of primitive types are wrapped in instances of the
     *               appropriate primitive wrapper class, such as
     *               {@code java.lang.Integer} or {@code java.lang.Boolean}.
     * @return 方法调用结果
     * @throws Throwable 序列化异常等
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 构建RPC请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        // 序列化请求数据
        byte[] bodyBytes = serializer.serialize(rpcRequest);

        // TODO 利用注册中心发现服务
        // 发送HTTP请求并处理响应，这里先写死
        try (HttpResponse httpResponse = HttpRequest.post(SERVICE_URL)
                .body(bodyBytes)
                .execute()) {

            if (httpResponse.isOk()) {
                byte[] result = httpResponse.bodyBytes();
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);

                // 返回响应数据
                return rpcResponse.getData();
            } else {
                log.error("Failed to call service. HTTP Status: {}, Response: {}",
                        httpResponse.getStatus(), httpResponse.body());
                throw new RuntimeException("Service call failed with HTTP status: " + httpResponse.getStatus());
            }
        } catch (Exception e) {
            log.error("Error occurred during service invocation. Method: {}, Args: {}, Error: {}",
                    method.getName(), args, e.getMessage(), e);
            throw e;
        }
    }
}