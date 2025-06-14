package com.rickey.example.spring.boot.provider.service;

import com.rickey.example.spring.boot.provider.mapper.UserMapper;
import com.rickey.example.spring.boot.provider.model.UserPO;
import com.rickey.example.spring.boot.provider.util.ConvertUtils;
import com.rickey.rpc.common.model.User;
import com.rickey.rpc.common.service.UserService;
import com.rickey.rpc.spring.boot.starter.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 用户服务实现类
 * @Author: rickey-c
 * @Date: 2025/6/3 17:27
 */
@Service
@RpcService
@Slf4j
//@DubboService(timeout = 300)
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUser(Long id) {
        log.info("user id is : {}", id);
        UserPO userPO = userMapper.selectById(id);
        return ConvertUtils.convertToUser(userPO);
    }

    @Override
    public String addUser(User user) {
//        UserPO userPO = ConvertUtils.convertToUserPO(user);
        return "success";
    }
}
