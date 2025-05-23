package com.rickey.rpc.proxy;

import com.rickey.rpc.RpcApplication;

import java.lang.reflect.Proxy;

/**
 * @Description: 动态代理工厂
 * @Author: rickey-c
 * @Date: 2025/5/17 23:13
 */
public class ServiceProxyFactory {

    /**
     * 根据服务类获取代理对象
     *
     * @param serviceClass 服务类
     * @param <T>          服务类型
     * @return 代理对象
     */
    public static <T> T getProxy(Class<T> serviceClass) {
        if (RpcApplication.getRpcConfig().isMock()) {
            return getMockProxy(serviceClass);
        }

        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy()
        );
    }

    /**
     * 根据服务类获取Mock代理对象
     *
     * @param serviceClass 服务类
     * @param <T>          代理对象
     * @return 服务类型
     */
    public static <T> T getMockProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new MockServiceProxy());
    }
}
