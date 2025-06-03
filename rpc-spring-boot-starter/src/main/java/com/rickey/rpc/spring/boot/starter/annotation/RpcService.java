package com.rickey.rpc.spring.boot.starter.annotation;

import com.rickey.rpc.constant.RpcConstant;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 服务提供者注解
 * @Author: rickey-c
 * @Date: 2025/6/3 15:19
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcService {

    /**
     * 服务接口类
     *
     * @return 服务接口
     */
    Class<?> interfaceClass() default void.class;

    /**
     * 版本
     *
     * @return 版本
     */
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;
}
