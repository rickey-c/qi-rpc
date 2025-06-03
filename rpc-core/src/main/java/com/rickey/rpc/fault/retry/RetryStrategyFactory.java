package com.rickey.rpc.fault.retry;


import com.rickey.rpc.spi.SpiLoader;

/**
 * @Description: 重试策略工厂
 * @Author: rickey-c
 * @Date: 2025/6/3 13:58
 */
public class RetryStrategyFactory {

    static {
        SpiLoader.load(RetryStrategy.class);
    }

    /**
     * 默认重试器
     */
    private static final RetryStrategy DEFAULT_RETRY_STRATEGY = new NoRetryStrategy();

    /**
     * 获取实例
     *
     * @param key 键名
     * @return 重试策略
     */
    public static RetryStrategy getInstance(String key) {
        return SpiLoader.getInstance(RetryStrategy.class, key);
    }

}
