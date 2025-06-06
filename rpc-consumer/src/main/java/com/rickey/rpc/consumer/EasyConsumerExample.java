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
        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("rickey");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            log.info(newUser.getName());
        } else {
            log.warn("user == null");
        }
        int number = userService.getNumber();
        log.info("number is : {}",number);
    }
}
