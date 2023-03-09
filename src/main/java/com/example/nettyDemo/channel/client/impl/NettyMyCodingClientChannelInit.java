package com.example.nettyDemo.channel.client.impl;

import com.example.nettyDemo.NettyHandlerCollect;
import com.example.nettyDemo.channel.client.NettyClientChannelInit;
import com.example.nettyDemo.channel.server.NettyServerChannelInit;
import com.example.nettyDemo.codec.MyNettyDecoder;
import com.example.nettyDemo.codec.MyNettyEncoder;
import com.example.nettyDemo.config.client.NettyClientConfig;
import com.example.nettyDemo.config.server.NettyServerConfig;
import com.example.nettyDemo.handler.client.NettyClientHeartbeatHandler;
import com.example.nettyDemo.handler.server.NettyServerHeartbeatHandler;
import io.netty.channel.Channel;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/6
 */
public class NettyMyCodingClientChannelInit implements NettyClientChannelInit {


    @Override
    public void initChannelHandlers(Channel channel, NettyClientConfig nettyClientConfig, ApplicationContext context) {
        channel.pipeline().addLast(new MyNettyDecoder(
                nettyClientConfig.getMyConfig().getMaxFrameLength(),
                nettyClientConfig.getMyConfig().getLengthFieldOffset(),
                nettyClientConfig.getMyConfig().getLengthFieldLength(),
                nettyClientConfig.getMyConfig().getLengthAdjustment(),
                nettyClientConfig.getMyConfig().getInitialBytesToStrip())
        );
        channel.pipeline().addLast(new MyNettyEncoder());
        // 心跳机制
        channel.pipeline().addLast(new NettyClientHeartbeatHandler(nettyClientConfig,context));
        // 自定义handler添加
        addChannelHandlers(channel);
    }
}
