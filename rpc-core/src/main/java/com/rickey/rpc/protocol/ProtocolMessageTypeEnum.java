package com.rickey.rpc.protocol;

import lombok.Getter;

/**
 * @Description: 协议消息的类型枚举
 * @Author: rickey-c
 * @Date: 2025/5/26 00:40
 */
@Getter
public enum ProtocolMessageTypeEnum {

    /**
     * 请求消息
     */
    REQUEST(0),

    /**
     * 响应消息
     */
    RESPONSE(1),

    /**
     * 心跳消息
     */
    HEART_BEAT(2),

    /**
     * 其他类型消息
     */
    OTHERS(3);

    /**
     * 类型标识
     */
    private final int key;

    /**
     * 构造方法
     *
     * @param key 类型标识
     */
    ProtocolMessageTypeEnum(int key) {
        this.key = key;
    }

    /**
     * 根据 key 获取对应的枚举实例
     *
     * @param key 类型标识
     * @return 对应的枚举实例，未找到返回 null
     */
    public static ProtocolMessageTypeEnum getEnumByKey(int key) {
        for (ProtocolMessageTypeEnum anEnum : ProtocolMessageTypeEnum.values()) {
            if (anEnum.key == key) {
                return anEnum;
            }
        }
        return null;
    }
}