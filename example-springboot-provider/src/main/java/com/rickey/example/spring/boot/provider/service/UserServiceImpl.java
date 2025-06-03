package com.rickey.example.spring.boot.provider.service;

import com.rickey.rpc.common.model.User;
import com.rickey.rpc.common.service.UserService;
import com.rickey.rpc.spring.boot.starter.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户服务实现类
 * @Author: rickey-c
 * @Date: 2025/6/3 17:27
 */
@Service
@RpcService
@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        //todo 注解驱动测试失败
        log.info("user name is : {}",user.getName());
        return user;
    }
}
