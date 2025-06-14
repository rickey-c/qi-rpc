package com.rickey.example.spring.boot.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rickey.example.spring.boot.provider.model.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: rickey-c
 * @Date: 2025/6/13 0:49
 */
@Mapper
@Component
public interface UserMapper extends BaseMapper<UserPO> {
}
