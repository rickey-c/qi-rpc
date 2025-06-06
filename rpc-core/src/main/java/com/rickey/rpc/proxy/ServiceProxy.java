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
import com.rickey.rpc.server.tcp.VertxTcpClient;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rickey.rpc.constant.RpcConstant.METHOD_NAME;

/**
 * @Description: 动态代理类(JDK动态代理)
 * @Author: rickey-c
 * @Date: 2025/5/17 23:03
 */
@Slf4j
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return 调用结果
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());

        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        String serviceKey = serviceMetaInfo.getServiceKey();

        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceKey);
        if (CollUtil.isEmpty(serviceMetaInfoList)) {
            log.error("服务发现失败，serviceMetaInfoList为空");
            throw new RuntimeException("暂无服务地址");
        }

        LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
        Map<String, Object> requestParams = new HashMap<>(8);
        requestParams.put(METHOD_NAME, rpcRequest.getMethodName());
        ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

        RpcResponse rpcResponse;
        try {
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
            );
            log.info("RPC请求成功: {}", rpcResponse);
            return rpcResponse.getData();
        } catch (Exception e) {
            log.error("RPC请求异常，进入容错处理", e);
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
            rpcResponse = tolerantStrategy.doTolerant(null, e);
            log.info("容错处理结果: {}", rpcResponse);
        }

        return rpcResponse.getData();
    }
}