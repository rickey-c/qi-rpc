package com.rickey.rpc.fault.retry;


import com.rickey.rpc.response.RpcResponse;

import java.util.concurrent.Callable;

/**
 * @Description: 重试策略
 * @Author: rickey-c
 * @Date: 2025/6/3 13:45
 */
public interface RetryStrategy {

    /**
     * 重试
     *
     * @param callable
     * @return
     * @throws Exception
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
