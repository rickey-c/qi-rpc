package com.rickey.rpc.consumer;

import com.rickey.rpc.common.model.User;
import com.rickey.rpc.common.service.UserService;
import com.rickey.rpc.proxy.ServiceProxyFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 简单实现消费功能
 * @Author: rickey-c
 * @Date: 2025/5/17 21:50
 */
@Slf4j
public class EasyConsumerExample {
    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User rickey = userService.getUser(new User("rickey"));
        System.out.println(rickey);
    }
}
