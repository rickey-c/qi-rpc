package com.rickey.example.spring.boot.consume.controller;

import com.rickey.rpc.common.model.User;
import com.rickey.rpc.common.response.ApiResponse;
import com.rickey.rpc.common.service.UserService;
import com.rickey.rpc.spring.boot.starter.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
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
public class ConsumerController {

    @DubboReference
    private UserService userService;

    @RpcReference
    private UserService userServiceRPC;

    @PostMapping("/rpc/qi")
    public ApiResponse<Void> testQiRpc(@RequestBody User req) {
        String msg = userService.addUser(req);
        log.info("qi rpc response is : {}",msg);
        return ApiResponse.success();
    }
    
    @PostMapping("/rpc/dubbo")
    public ApiResponse<Void> testDubboRpc(@RequestBody User req) {
        String msg = userService.addUser(req);
        log.info("dubbo rpc response is : {}",msg);
        return ApiResponse.success();
    }

}
