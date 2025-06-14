package com.rickey.example.spring.boot.provider;

import com.rickey.rpc.spring.boot.starter.annotation.EnableRpc;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.rickey.example.spring.boot.provider.mapper")
//@EnableRpc
@EnableDubbo
public class ExampleSpringbootProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleSpringbootProviderApplication.class, args);
    }

}
