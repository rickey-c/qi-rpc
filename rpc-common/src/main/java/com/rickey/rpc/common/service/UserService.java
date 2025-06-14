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
     *
     * @param id userId
     * @return User用户
     */
    User getUser(Long id);

    /**
     * test mock
     *
     * @return number
     */
    default int getNumber() {
        return 1;
    }

    /**
     * 添加用户
     *
     * @param user user
     * @return
     */
    String addUser(User user) throws InterruptedException;
}
