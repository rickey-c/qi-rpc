package com.rickey.example.spring.boot.consume.controller;

import com.rickey.rpc.common.model.User;
import com.rickey.rpc.common.service.DubboDemoService;
import com.rickey.rpc.common.service.UserService;
import com.rickey.rpc.spring.boot.starter.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.web.bind.annotation.GetMapping;
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

    @DubboReference
    private DubboDemoService dubboDemoService;

    @PostMapping("/rpc/qi")
    public String testQiRpc(@RequestBody User req) {
        User user = userService.getUser(req);
        return user.getName();
    }

    @GetMapping("/rpc/dubbo")
    public String testDubbo() {
        return dubboDemoService.test();
    }

}
