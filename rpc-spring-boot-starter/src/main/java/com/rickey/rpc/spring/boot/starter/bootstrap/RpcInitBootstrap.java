package com.rickey.rpc.spring.boot.starter.bootstrap;

import com.rickey.rpc.RpcApplication;
import com.rickey.rpc.config.RpcConfig;
import com.rickey.rpc.server.VertexHttpServer;
import com.rickey.rpc.spring.boot.starter.annotation.EnableRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Description: RPC框架启动
 * @Author: rickey-c
 * @Date: 2025/6/3 16:36
 */
@Slf4j
public class RpcInitBootstrap implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean needServer = (boolean) importingClassMetadata.getAnnotationAttributes(EnableRpc.class.getName()).get("needServer");

        RpcApplication.init();

        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        if (needServer) {
            VertexHttpServer vertexHttpServer = new VertexHttpServer();
            vertexHttpServer.start(rpcConfig.getServerPort());
        } else {
            log.info("不启动 server");
        }
    }
}
