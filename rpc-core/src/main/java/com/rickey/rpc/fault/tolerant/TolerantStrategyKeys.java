package com.rickey.rpc.fault.tolerant;

/**
 * @Description: 容错策略常用键名
 * @Author: rickey-c
 * @Date: 2025/6/3 13:15
 */
public interface TolerantStrategyKeys {

    /**
     * 故障恢复
     */
    String FAIL_BACK = "failBack";

    /**
     * 快速失败
     */
    String FAIL_FAST = "failFast";

    /**
     * 故障转移
     */
    String FAIL_OVER = "failOver";

    /**
     * 静默处理
     */
    String FAIL_SAFE = "failSafe";

}
