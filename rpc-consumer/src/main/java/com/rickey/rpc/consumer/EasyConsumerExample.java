package com.rickey.rpc.consumer;

import com.rickey.rpc.common.model.User;
import com.rickey.rpc.common.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 简单实现消费功能
 * @Author: rickey-c
 * @Date: 2025/5/17 21:50
 */
@Slf4j
public class EasyConsumerExample {
    public static void main(String[] args) {
        // TODO等待RPC实现服务调用
        UserService userService = null;
        User user = new User();
        user.setName("rickey");
        User userGetFromRPC = userService.getUser(user);
        // 这里还没有RPC会报NPE
        if (userGetFromRPC == null) {
            log.error("can not access user");
        } else {
            log.info("userGetFromRPC = {}", userGetFromRPC);
        }
    }
}
