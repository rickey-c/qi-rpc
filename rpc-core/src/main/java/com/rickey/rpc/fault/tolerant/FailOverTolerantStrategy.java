package com.rickey.rpc.fault.tolerant;

import cn.hutool.core.collection.CollUtil;
import com.rickey.rpc.RpcApplication;
import com.rickey.rpc.config.RpcConfig;
import com.rickey.rpc.fault.retry.RetryStrategy;
import com.rickey.rpc.fault.retry.RetryStrategyFactory;
import com.rickey.rpc.loadbalancer.LoadBalancer;
import com.rickey.rpc.loadbalancer.LoadBalancerFactory;
import com.rickey.rpc.model.ServiceMetaInfo;
import com.rickey.rpc.request.RpcRequest;
import com.rickey.rpc.response.RpcResponse;
import com.rickey.rpc.server.tcp.VertxTcpClient;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rickey.rpc.constant.RpcConstant.METHOD_NAME;

/**
 * @Description: 转移到其他服务节点
 * @Author: rickey-c
 * @Date: 2025/6/3 13:08
 */
@Slf4j
public class FailOverTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        //获取其它节点并调用
        RpcRequest rpcRequest = (RpcRequest) context.get("rpcRequest");
        List<ServiceMetaInfo> serviceMetaInfoList = (List<ServiceMetaInfo>) context.get("serviceMetaInfoList");
        ServiceMetaInfo selectedServiceMetaInfo = (ServiceMetaInfo) context.get("selectedServiceMetaInfo");

        //移除失败节点
        removeFailNode(selectedServiceMetaInfo, serviceMetaInfoList);

        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
        Map<String, Object> requestParamMap = new HashMap<>(8);
        requestParamMap.put(METHOD_NAME, rpcRequest.getMethodName());

        RpcResponse rpcResponse;
        while (!serviceMetaInfoList.isEmpty()) {
            ServiceMetaInfo currentServiceMetaInfo = loadBalancer.select(requestParamMap, serviceMetaInfoList);
            System.out.println("获取节点：" + currentServiceMetaInfo);
            try {
                //发送tcp请求
                RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
                rpcResponse = retryStrategy.doRetry(() -> VertxTcpClient.doRequest(rpcRequest, currentServiceMetaInfo));
                return rpcResponse;
            } catch (Exception exception) {
                //移除失败节点
                removeFailNode(currentServiceMetaInfo, serviceMetaInfoList);
            }
        }
        //调用失败
        throw new RuntimeException(e);
    }

    /**
     * 移除失败节点，可考虑下线
     *
     * @param serviceMetaInfoList 服务元信息列表
     */
    private void removeFailNode(ServiceMetaInfo currentServiceMetaInfo, List<ServiceMetaInfo> serviceMetaInfoList) {
        if (CollUtil.isNotEmpty(serviceMetaInfoList)) {
            serviceMetaInfoList.removeIf(next -> currentServiceMetaInfo.getServiceNodeKey().equals(next.getServiceNodeKey()));
        }
    }
}
