package com.rickey.rpc.loadbalancer;

import com.rickey.rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * @Description: 负载均衡接口
 * @Author: rickey-c
 * @Date: 2025/6/3 11:34
 */
public interface LoadBalancer {

    /**
     * 选择服务调用
     *
     * @param requestParams       请求参数
     * @param serviceMetaInfoList 服务元信息列表
     * @return 进行调用的服务
     */
    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
