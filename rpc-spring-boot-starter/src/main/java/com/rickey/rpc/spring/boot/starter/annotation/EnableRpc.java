package com.rickey.rpc.spring.boot.starter.annotation;

import com.rickey.rpc.spring.boot.starter.bootstrap.RpcConsumerBootstrap;
import com.rickey.rpc.spring.boot.starter.bootstrap.RpcInitBootstrap;
import com.rickey.rpc.spring.boot.starter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 启用RPC注解
 * @Author: rickey-c
 * @Date: 2025/6/3 15:15
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcConsumerBootstrap.class, RpcProviderBootstrap.class})
public @interface EnableRpc {

    /**
     * 需要启动服务
     *
     * @return 需要启动服务情况
     */
    boolean needServer() default true;
}
