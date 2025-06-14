package com.rickey.example.spring.boot.provider.mapper;

import com.rickey.example.spring.boot.provider.model.UserPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Description:
 * @Author: rickey-c
 * @Date: 2025/6/14 11:55
 */
@SpringBootTest
public class UserMapperTest {
    
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsertAndSelect() {
        UserPO user = new UserPO();
        user.setName("Tom");
        user.setAge(20);
        user.setEmail("tom@example.com");

        int rows = userMapper.insert(user);
        assertThat(rows).isEqualTo(1);
        assertThat(user.getId()).isNotNull();

        UserPO dbUser = userMapper.selectById(user.getId());
        assertThat(dbUser).isNotNull();
        assertThat(dbUser.getName()).isEqualTo("Tom");
    }
    
}
