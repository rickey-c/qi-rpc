package com.rickey.rpc.common.service;

import com.rickey.rpc.common.model.User;

/**
 * @Description: 用户接口，用于获取用户
 * @Author: rickey-c
 * @Date: 2025/5/17 21:33
 */
public interface UserService {
    /**
     * 获取用户
     * @param user user
     * @return User用户
     */
    User getUser(User user);
}
