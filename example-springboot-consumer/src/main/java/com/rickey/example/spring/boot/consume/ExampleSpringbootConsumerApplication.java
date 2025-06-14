package com.rickey.example.spring.boot.consume;

import com.rickey.rpc.spring.boot.starter.annotation.EnableRpc;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRpc(needServer = false)
//@EnableDubbo
public class ExampleSpringbootConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleSpringbootConsumerApplication.class, args);
    }

}
