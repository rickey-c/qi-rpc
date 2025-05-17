package com.rickey.rpc.serializer;

import java.io.*;

/**
 * @Description: JDK序列化器，使用 JDK 自带的序列化机制实现对象与字节数组之间的转换。
 * @Author: rickey-c
 * @Date: 2025/5/17 22:32
 */
public class JdkSerializer implements Serializer {

    /**
     * 将对象序列化为字节数组
     *
     * @param object 目标对象
     * @param <T>    对象类型
     * @return 序列化后的字节数组
     * @throws IOException 如果序列化过程中发生 I/O 异常
     */
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        // 创建字节输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 创建对象输出流
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        // 将对象写入输出流
        objectOutputStream.writeObject(object);
        // 关闭对象输出流
        objectOutputStream.close();
        // 返回序列化后的字节数组
        return outputStream.toByteArray();
    }

    /**
     * 将字节数组反序列化为对象
     *
     * @param bytes 要反序列化的字节数组
     * @param type  目标对象的类型
     * @param <T>   对象类型
     * @return 反序列化后的对象
     * @throws IOException 如果反序列化过程中发生 I/O 异常
     */
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        // 创建字节输入流
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        // 创建对象输入流
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            // 从输入流中读取对象并返回
            return (T) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            // 如果类未找到，抛出运行时异常
            throw new RuntimeException(e);
        }
        // 关闭对象输入流
    }
}