package com.example.nettyDemo.channel.server.impl;

import com.example.nettyDemo.channel.server.NettyServerChannelInit;
import com.example.nettyDemo.config.server.NettyServerConfig;
import io.netty.channel.Channel;
import org.springframework.context.ApplicationContext;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/9
 */
public class NettyDefaultServerChannelInit implements NettyServerChannelInit {
    @Override
    public void initChannelHandlers(Channel channel, NettyServerConfig nettyServerConfig, ApplicationContext context) {
        addChannelHandlers(channel);
    }
}
