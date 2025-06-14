package com.rickey.example.spring.boot.provider.util;

import com.rickey.example.spring.boot.provider.model.UserPO;
import com.rickey.rpc.common.model.User;

/**
 * @Description:
 * @Author: rickey-c
 * @Date: 2025/6/13 1:01
 */
public class ConvertUtils {

    /**
     * 工具方法：将User对象转换为UserPO对象
     */
    public static UserPO convertToUserPO(User user) {
        if (user == null) {
            return null;
        }
        UserPO userPO = new UserPO();
        userPO.setId(user.getId());
        userPO.setName(user.getName());
        userPO.setAge(user.getAge());
        userPO.setEmail(user.getEmail());
        return userPO;
    }

    public static User convertToUser(UserPO userPO) {
        if (userPO == null) {
            return null;
        }
        User user = new User();
        user.setId(userPO.getId());
        user.setName(userPO.getName());
        user.setEmail(userPO.getEmail());
        // 根据UserPO和User的字段补充其他属性
        return user;
    }
}
