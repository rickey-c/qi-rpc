package com.rickey.rpc.spring.boot.starter.bootstrap;

import com.rickey.rpc.RpcApplication;
import com.rickey.rpc.config.RegistryConfig;
import com.rickey.rpc.config.RpcConfig;
import com.rickey.rpc.model.ServiceMetaInfo;
import com.rickey.rpc.registry.LocalRegistry;
import com.rickey.rpc.registry.Registry;
import com.rickey.rpc.registry.RegistryFactory;
import com.rickey.rpc.spring.boot.starter.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @Description:
 * @Author: rickey-c
 * @Date: 2025/6/3 16:40
 */
@Slf4j
public class RpcProviderBootstrap implements BeanPostProcessor {

    /**
     * Bean初始化后执行，注册服务
     * 识别带有@RpcService注解的Bean，将其注册到本地注册表和远程注册中心
     *
     * @param bean     bean对象实例
     * @param beanName bean的名称
     * @return 原始bean对象或经过处理后的bean对象
     * @throws BeansException 当Bean处理过程中出现异常时抛出
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        RpcService rpcService = beanClass.getAnnotation(RpcService.class);
        if (rpcService != null) {
            // 1.获取服务的基础信息
            Class<?> interfaceClass = rpcService.interfaceClass();
            if (interfaceClass == void.class) {
                interfaceClass = beanClass.getInterfaces()[0];
            }
            String serviceName = interfaceClass.getName();
            String serviceVersion = rpcService.serviceVersion();
            // 2.注册服务
            // 本地注册
            LocalRegistry.register(serviceName, beanClass);
            // 注册服务到注册中心
            final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(serviceVersion);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + "服务注册失败" + e);
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
