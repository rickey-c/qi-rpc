package com.rickey.example.spring.boot.consume;

import com.rickey.example.spring.boot.consume.service.ExampleServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ExampleServiceImplTest {

    @Resource
    private ExampleServiceImpl exampleService;

    @Test
    void test1() {
        exampleService.test();
    }
}
