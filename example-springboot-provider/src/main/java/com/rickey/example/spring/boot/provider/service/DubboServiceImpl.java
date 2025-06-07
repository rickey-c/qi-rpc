package com.rickey.example.spring.boot.provider.service;

import com.rickey.rpc.common.service.DubboDemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: rickey-c
 * @Date: 2025/6/7 13:55
 */
@Service
@DubboService
public class DubboServiceImpl implements DubboDemoService {
    @Override
    public String test() {
        return "Hello Dubbo";
    }
}
