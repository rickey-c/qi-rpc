package com.rickey.example.spring.boot.consume;

import com.rickey.example.spring.boot.consume.service.ExampleServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExampleServiceImplTest {

    @Autowired
    private ExampleServiceImpl exampleService;

    @Test
    void exampleUserServiceTest() {
        exampleService.test();
    }
    
}
