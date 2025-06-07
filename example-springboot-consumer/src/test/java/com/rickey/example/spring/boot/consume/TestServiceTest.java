package com.rickey.example.spring.boot.consume;

import com.rickey.example.spring.boot.consume.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestServiceTest {

    @Autowired
    private TestService testService;

    @Test
    void exampleUserServiceTest() {
        testService.test();
    }

}
