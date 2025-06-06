package com.rickey.rpc.spring.boot.starter.bootstrap;

import com.rickey.rpc.proxy.ServiceProxyFactory;
import com.rickey.rpc.spring.boot.starter.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;

/**
 * RPC消费者启动类，用于在Spring容器初始化后，
 * 扫描并为带有&#64;RpcReference注解的字段注入代理对象。
 *
 * @Author: rickey-c
 * @Date: 2025/6/3 16:50
 */
@Slf4j
public class RpcConsumerBootstrap implements BeanPostProcessor {

    /**
     * 在 bean 初始化后处理，扫描并为带有 {@link RpcReference} 注解的字段注入代理对象。
     *
     * @param bean     当前 bean 实例
     * @param beanName bean 名称
     * @return 处理后的 bean 实例
     * @throws BeansException 处理异常
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, @NonNull String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        // 遍历对象的所有属性
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field field : declaredFields) {
            RpcReference rpcReference = field.getAnnotation(RpcReference.class);
            if (rpcReference != null) {
                // 为属性生成代理对象
                Class<?> interfaceClass = rpcReference.interfaceClass();
                if (interfaceClass == void.class) {
                    interfaceClass = field.getType();
                }
                field.setAccessible(true);
                Object proxyObject = ServiceProxyFactory.getProxy(interfaceClass);
                try {
                    field.set(bean, proxyObject);
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("为字段注入代理对象失败", e);
                }
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

}