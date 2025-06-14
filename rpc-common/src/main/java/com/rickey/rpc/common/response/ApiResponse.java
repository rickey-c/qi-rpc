package com.rickey.rpc.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 响应类
 * @Author: rickey-c
 * @Date: 2025/5/17 21:27
 */
@Data
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 私有构造函数，防止外部直接实例化
     */
    private ApiResponse() {
    }

    private ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功响应（不带数据）
     *
     * @return ApiResponse<Void>
     */
    public static ApiResponse<Void> success() {
        return new ApiResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功响应（带数据）
     *
     * @param data 响应数据
     * @return ApiResponse<T>
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    /**
     * 失败响应（使用预定义错误码）
     *
     * @param errorCode 错误码枚举
     * @return ApiResponse<Void>
     */
    public static ApiResponse<Void> error(ResponseCode errorCode) {
        return new ApiResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败响应（使用预定义错误码和自定义消息）
     *
     * @param errorCode 错误码枚举
     * @param message   自定义错误消息
     * @return ApiResponse<Void>
     */
    public static ApiResponse<Void> error(ResponseCode errorCode, String message) {
        return new ApiResponse<>(errorCode.getCode(), message, null);
    }

    /**
     * 失败响应（使用自定义错误码和消息）
     *
     * @param code    自定义状态码
     * @param message 自定义消息
     * @return ApiResponse<Void>
     */
    public static ApiResponse<Void> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    /**
     * 内部枚举，定义常用状态码
     */
    public enum ResponseCode {
        SUCCESS(200, "操作成功"),
        FAILED(500, "操作失败"),
        VALIDATE_FAILED(400, "参数校验失败"),
        UNAUTHORIZED(401, "暂未登录或token已经过期"),
        FORBIDDEN(403, "没有相关权限");

        private final int code;
        private final String message;

        ResponseCode(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
