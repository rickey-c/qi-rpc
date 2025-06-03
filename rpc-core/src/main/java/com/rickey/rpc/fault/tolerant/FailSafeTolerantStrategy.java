package com.rickey.rpc.fault.tolerant;

import com.rickey.rpc.response.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Description: 静默处理异常
 * @Author: rickey-c
 * @Date: 2025/6/3 13:10
 */
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理异常", e);
        return new RpcResponse();
    }
}
