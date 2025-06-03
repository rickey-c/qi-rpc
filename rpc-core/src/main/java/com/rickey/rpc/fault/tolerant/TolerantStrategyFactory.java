package com.rickey.rpc.fault.tolerant;

import com.rickey.rpc.spi.SpiLoader;

/**
 * @Description: 容错策略工厂
 * @Author: rickey-c
 * @Date: 2025/6/3 13:13
 */
public class TolerantStrategyFactory {

    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错策略
     */
    private static final TolerantStrategy DEFAULT_RETRY_STRATEGY = new FailFastTolerantStrategy();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static TolerantStrategy getInstance(String key) {
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }

}
