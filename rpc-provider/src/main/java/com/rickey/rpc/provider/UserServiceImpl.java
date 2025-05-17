package com.rickey.rpc.provider;

import com.rickey.rpc.common.model.User;
import com.rickey.rpc.common.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 用户服务实现类
 * @Author: rickey-c
 * @Date: 2025/5/17 21:41
 */
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        log.info("User.name = {}",user.getName());
        return user;
    }
}
