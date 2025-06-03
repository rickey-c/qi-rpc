package com.rickey.rpc.fault.tolerant;


import com.rickey.rpc.response.RpcResponse;

import java.util.Map;

/**
 * @Description: 快速失败
 * @Author: rickey-c
 * @Date: 2025/6/3 13:05
 */
public class FailFastTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错", e);
    }
}
