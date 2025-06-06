package com.rickey.example.spring.boot.consume.service;

import com.rickey.rpc.common.model.User;
import com.rickey.rpc.common.service.UserService;
import com.rickey.rpc.spring.boot.starter.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description: testService
 * @Author: rickey-c
 * @Date: 2025/6/3 17:29
 */
@Slf4j
@Service
public class TestService {
    
    @RpcReference
    private UserService userService;
    
    public void test(){
        User user = new User();
        user.setName("rickey");
        User userServiceUser = userService.getUser(user);
        log.info("user get from service : {}",userServiceUser);
    }
}
