package com.example.nettyDemo.channel.client.impl;

import com.example.nettyDemo.annotation.NettyClientHandler;
import com.example.nettyDemo.channel.client.NettyClientChannelInit;
import com.example.nettyDemo.channel.server.NettyServerChannelInit;
import com.example.nettyDemo.config.client.NettyClientConfig;
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
public class NettyDefaultClientChannelInit implements NettyClientChannelInit {

    @Override
    public void initChannelHandlers(Channel channel, NettyClientConfig nettyClientConfig, ApplicationContext context) {
        addChannelHandlers(channel);
    }
}
