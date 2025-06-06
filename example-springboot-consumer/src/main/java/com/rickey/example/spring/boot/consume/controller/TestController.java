package com.rickey.example.spring.boot.consume.controller;

import com.rickey.example.spring.boot.consume.service.TestService;
import com.rickey.rpc.common.model.User;
import com.rickey.rpc.common.service.UserService;
import com.rickey.rpc.spring.boot.starter.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: controller 暴露API用于测试
 * @Author: rickey-c
 * @Date: 2025/6/6 22:02
 */
@Slf4j
@RestController
public class TestController {

    @RpcReference
    private UserService userService;

    @PostMapping("/test")
    public String testUser(@RequestBody User req) {
        User user = userService.getUser(req);
        return user.getName();
    }
}
