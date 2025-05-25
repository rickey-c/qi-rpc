package com.rickey.rpc.protocol;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 协议消息的序列化器枚举
 * @Author: rickey-c
 * @Date: 2025/5/26 00:50
 */
@Getter
public enum ProtocolMessageSerializerEnum {

    /**
     * JDK 原生序列化
     */
    JDK(0, "jdk"),

    /**
     * JSON 序列化
     */
    JSON(1, "json"),

    /**
     * Kryo 序列化
     */
    KRYO(2, "kryo"),

    /**
     * Hessian 序列化
     */
    HESSIAN(3, "hessian");

    /**
     * 序列化器类型标识
     */
    private final int key;

    /**
     * 序列化器名称
     */
    private final String value;

    /**
     * 构造方法
     *
     * @param key   类型标识
     * @param value 名称
     */
    ProtocolMessageSerializerEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 获取所有序列化器名称的列表
     *
     * @return 名称列表
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据类型标识获取对应的枚举实例
     *
     * @param key 类型标识
     * @return 对应枚举，未找到返回 null
     */
    public static ProtocolMessageSerializerEnum getEnumByKey(int key) {
        for (ProtocolMessageSerializerEnum anEnum : ProtocolMessageSerializerEnum.values()) {
            if (anEnum.key == key) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 根据名称获取对应的枚举实例
     *
     * @param value 名称
     * @return 对应枚举，未找到返回 null
     */
    public static ProtocolMessageSerializerEnum getEnumByValue(String value) {
        if (ObjectUtil.isEmpty(value)) {
            return null;
        }
        for (ProtocolMessageSerializerEnum anEnum : ProtocolMessageSerializerEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}