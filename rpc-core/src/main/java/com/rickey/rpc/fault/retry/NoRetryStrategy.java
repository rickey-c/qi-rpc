package com.rickey.rpc.fault.retry;

import com.rickey.rpc.response.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @Description: 不重试
 * @Author: rickey-c
 * @Date: 2025/6/3 13:48
 */
@Slf4j
public class NoRetryStrategy implements RetryStrategy {

    /**
     * 重试
     *
     * @param callable Callable参数
     * @return 响应
     * @throws ExecutionException 执行异常
     */
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }

}
