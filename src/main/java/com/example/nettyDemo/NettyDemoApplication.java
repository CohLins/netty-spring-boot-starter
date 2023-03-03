package com.example.nettyDemo;


import com.example.nettyDemo.entity.NettyHttpServerEntity;
import com.example.nettyDemo.event.NettyHttpServerEvent;
import io.netty.channel.ChannelHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.Map;

@SpringBootApplication
public class NettyDemoApplication implements CommandLineRunner {


    @Resource
    private ApplicationContext context;

    public static void main(String[] args)  {
        SpringApplication.run(NettyDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
