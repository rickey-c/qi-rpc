package com.rickey.rpc.fault.retry;

/**
 * @Description: 重试策略键名
 * @Author: rickey-c
 * @Date: 2025/6/3 14:05
 */
public interface RetryStrategyKeys {

    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间间隔
     */
    String FIXED_INTERVAL = "fixedInterval";

}
