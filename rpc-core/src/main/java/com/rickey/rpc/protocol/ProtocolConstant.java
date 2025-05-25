package com.rickey.rpc.protocol;

/**
 * @Description: 协议常量
 * @Author: rickey-c
 * @Date: 2025/5/26 00:44
 */
public interface ProtocolConstant {

    /**
     * 消息头长度
     */
    int MESSAGE_HEADER_LENGTH = 17;

    /**
     * 协议魔数
     */
    byte PROTOCOL_MAGIC = 0x1;

    /**
     * 协议版本号
     */
    byte PROTOCOL_VERSION = 0x1;
}
