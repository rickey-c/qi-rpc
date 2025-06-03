package com.rickey.rpc.spring.boot.starter.bootstrap;

import com.rickey.rpc.proxy.ServiceProxyFactory;
import com.rickey.rpc.spring.boot.starter.annotation.RpcReference;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * @Description: RPC消费者启动
 * @Author: rickey-c
 * @Date: 2025/6/3 16:50
 */
public class RpcConsumerBootstrap implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field field : declaredFields) {
            RpcReference reference = field.getAnnotation(RpcReference.class);
            if (reference != null) {
                Class<?> interfaceClass = reference.interfaceClass();
                if (interfaceClass == void.class) {
                    interfaceClass = field.getType();
                }
                field.setAccessible(true);
                Object proxy = ServiceProxyFactory.getProxy(interfaceClass);
                try {
                    field.set(bean, proxy);
                    field.setAccessible(true);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("为字段注入代理对象失败", e);
                }
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
