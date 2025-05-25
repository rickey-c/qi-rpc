package com.rickey.rpc.protocol;

import com.rickey.rpc.serializer.Serializer;
import com.rickey.rpc.serializer.SerializerFactory;
import io.vertx.core.buffer.Buffer;

import java.io.IOException;

/**
 * 协议消息编码器
 *
 * @Author: rickey-c
 * @Date: 2025/5/26 00:54
 */
public class ProtocolMessageEncoder {

    /**
     * 编码协议消息为 Buffer
     *
     * @param protocolMessage 协议消息对象
     * @return 编码后的 Buffer
     * @throws IOException 序列化异常
     */
    public static Buffer encode(ProtocolMessage<?> protocolMessage) throws IOException {
        // 判空处理
        if (protocolMessage == null || protocolMessage.getHeader() == null) {
            return Buffer.buffer();
        }
        ProtocolMessage.Header header = protocolMessage.getHeader();
        // 创建缓冲区并依次写入头部字段
        Buffer buffer = Buffer.buffer();
        buffer.appendByte(header.getMagic());      // 魔数
        buffer.appendByte(header.getVersion());    // 协议版本
        buffer.appendByte(header.getSerializer()); // 序列化方式
        buffer.appendByte(header.getType());       // 消息类型
        buffer.appendByte(header.getStatus());     // 状态码
        buffer.appendLong(header.getRequestId());  // 请求ID
        // 获取序列化器
        ProtocolMessageSerializerEnum serializerEnum = ProtocolMessageSerializerEnum.getEnumByKey(header.getSerializer());
        if (serializerEnum == null) {
            throw new RuntimeException("序列化协议不存在");
        }
        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getValue());
        // 序列化消息体
        byte[] bodyBytes = serializer.serialize(protocolMessage.getBody());
        // 写入消息体长度和内容
        buffer.appendInt(bodyBytes.length);
        buffer.appendBytes(bodyBytes);
        return buffer;
    }
}