package com.rickey.rpc.fault.tolerant;

import com.rickey.rpc.response.RpcResponse;

import java.util.Map;

/**
 * @Description: 容错策略
 * @Author: rickey-c
 * @Date: 2025/6/3 13:00
 */
public interface TolerantStrategy {

    /**
     * 容错
     *
     * @param context 上下文，用于传递数据
     * @param e       异常
     * @return 响应
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
