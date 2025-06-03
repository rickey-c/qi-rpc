package com.rickey.rpc.fault.tolerant;

import com.rickey.rpc.proxy.MockServiceProxy;
import com.rickey.rpc.response.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Description: 降级到其他服务
 * @Author: rickey-c
 * @Date: 2025/6/3 13:00
 */
@Slf4j
public class FailBackTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        MockServiceProxy mockServiceProxy = new MockServiceProxy();
        Object result = null;
        try {
            result = mockServiceProxy.invoke(null, (Method) context.get("method"), null);
        } catch (Throwable throwable) {
            log.info("fail back to mock 异常！", throwable);
        }
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setData(result);
        return rpcResponse;
    }
}

