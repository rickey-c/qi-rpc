package com.rickey.rpc.serializer;

import java.io.IOException;

/**
 * @Description: 序列化接口，定义了对象与字节数组之间的转换方法。
 * @Author: rickey-c
 * @Date: 2025/5/17 22:32
 */
public interface Serializer {
    /**
     * 将对象序列化为字节数组。
     *
     * @param object 要序列化的对象
     * @param <T> 对象的类型
     * @return 序列化后的字节数组
     * @throws IOException 如果序列化过程中发生 I/O 异常
     */
    <T> byte[] serialize(T object) throws IOException;

    /**
     * 将字节数组反序列化为对象。
     *
     * @param bytes 要反序列化的字节数组
     * @param type 目标对象的类型
     * @param <T> 目标对象的类型
     * @return 反序列化后的对象
     * @throws IOException 如果反序列化过程中发生 I/O 异常
     */
    <T> T deserialize(byte[] bytes, Class<T> type) throws IOException;

}