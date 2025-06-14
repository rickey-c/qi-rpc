package com.rickey.example.spring.boot.consume;

import com.rickey.example.spring.boot.consume.controller.ConsumerController;
import com.rickey.rpc.common.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestServiceTest {

    @Autowired
    private ConsumerController consumerController;

    @Test
    void exampleUserServiceTest() {
        User user = new User();
        user.setName("rice");
        user.setAge(19);
        user.setEmail("312");
        consumerController.testQiRpc(user);
    }

}
