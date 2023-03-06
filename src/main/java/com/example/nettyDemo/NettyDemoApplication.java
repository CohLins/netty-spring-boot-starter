package com.example.nettyDemo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

@SpringBootApplication
public class NettyDemoApplication {


    @Resource
    private ApplicationContext context;

    public static void main(String[] args)  {
        SpringApplication.run(NettyDemoApplication.class, args);
    }
}
