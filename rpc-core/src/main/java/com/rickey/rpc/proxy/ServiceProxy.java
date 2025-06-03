package com.rickey.rpc.proxy;

import cn.hutool.core.collection.CollUtil;
import com.rickey.rpc.RpcApplication;
import com.rickey.rpc.config.RpcConfig;
import com.rickey.rpc.constant.RpcConstant;
import com.rickey.rpc.fault.retry.RetryStrategy;
import com.rickey.rpc.fault.retry.RetryStrategyFactory;
import com.rickey.rpc.fault.tolerant.TolerantStrategy;
import com.rickey.rpc.fault.tolerant.TolerantStrategyFactory;
import com.rickey.rpc.loadbalancer.LoadBalancer;
import com.rickey.rpc.loadbalancer.LoadBalancerFactory;
import com.rickey.rpc.model.ServiceMetaInfo;
import com.rickey.rpc.registry.Registry;
import com.rickey.rpc.registry.RegistryFactory;
import com.rickey.rpc.request.RpcRequest;
import com.rickey.rpc.response.RpcResponse;
import com.rickey.rpc.serializer.Serializer;
import com.rickey.rpc.serializer.SerializerFactory;
import com.rickey.rpc.server.tcp.VertxTcpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 动态代理类(JDK动态代理)
 * @Author: rickey-c
 * @Date: 2025/5/17 23:03
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return 调用结果
     * @throws Throwable 异常
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        // 从注册中心获取服务提供者请求地址
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
        if (CollUtil.isEmpty(serviceMetaInfoList)) {
            throw new RuntimeException("暂无服务地址");
        }
        // 负载均衡
        LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
        // 将调用方法名（请求路径）作为负载均衡参数
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodName", rpcRequest.getMethodName());
        ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
        // rpc 请求
        // 使用重试机制
        RpcResponse rpcResponse;
        try {
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
            );
        } catch (Exception e) {
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
            Map<String, Object> requestTolerantParamMap = new HashMap<>(8);
            requestTolerantParamMap.put("rpcRequest", rpcRequest);
            requestTolerantParamMap.put("selectedServiceMetaInfo", selectedServiceMetaInfo);
            requestTolerantParamMap.put("serviceMetaInfoList", serviceMetaInfoList);
            rpcResponse = tolerantStrategy.doTolerant(requestTolerantParamMap, e);
        }
        return rpcResponse.getData();
    }
}
