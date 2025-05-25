package com.rickey.rpc.protocol;

import lombok.Getter;

/**
 * @Description: 协议消息的状态枚举
 * @Author: rickey-c
 * @Date: 2025/5/26 00:40
 */
@Getter
public enum ProtocolMessageStatusEnum {

    /**
     * 正常，处理成功
     */
    OK("ok", 20),

    /**
     * 错误的请求（如参数错误、协议不符等）
     */
    BAD_REQUEST("badRequest", 400),

    /**
     * 错误的响应（如服务端异常、处理失败等）
     */
    BAD_RESPONSE("badResponse", 500);

    /**
     * 状态文本描述
     */
    private final String text;

    /**
     * 状态码
     */
    private final int value;

    /**
     * 构造方法
     *
     * @param text  状态文本
     * @param value 状态码
     */
    ProtocolMessageStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取对应的枚举实例
     *
     * @param value 状态码
     * @return 对应的枚举实例，未找到返回 null
     */
    public static ProtocolMessageStatusEnum getEnumByValue(int value) {
        for (ProtocolMessageStatusEnum anEnum : ProtocolMessageStatusEnum.values()) {
            if (anEnum.value == value) {
                return anEnum;
            }
        }
        return null;
    }
}