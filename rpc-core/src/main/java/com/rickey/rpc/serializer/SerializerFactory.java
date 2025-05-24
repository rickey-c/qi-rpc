package com.rickey.rpc.serializer;

import com.rickey.rpc.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 序列化器键名
 * @Author: rickey-c
 * @Date: 2025/5/24 23:42
 */
public class SerializerFactory {

    static {
        SpiLoader.loadAll();
    }

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     *
     * @param key 序列化方式key
     * @return 序列化器
     */
    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }

}
