package com.example.nettyDemo.handler.client;

import com.example.nettyDemo.config.client.NettyClientConfig;
import com.example.nettyDemo.config.client.NettyClientHeartConfig;
import com.example.nettyDemo.entity.NettyClientNettyIdleEntity;
import com.example.nettyDemo.event.NettyClientIdleEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;

public class NettyClientHeartbeatHandler extends IdleStateHandler {

    private static final Logger log = LoggerFactory.getLogger(NettyClientHeartbeatHandler.class);

    private NettyClientHeartConfig clientHeartConfig;

    private ApplicationContext applicationContext;

    public NettyClientHeartbeatHandler(NettyClientConfig clientConfig, ApplicationContext applicationContext) {
        super(clientConfig.getHeartConfig().getReadTime(), clientConfig.getHeartConfig().getWriteTime(), clientConfig.getHeartConfig().getAllTime(), TimeUnit.SECONDS);
        this.clientHeartConfig = clientConfig.getHeartConfig();
        this.applicationContext = applicationContext;
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        applicationContext.publishEvent(new NettyClientIdleEvent(new NettyClientNettyIdleEntity(ctx, evt, clientHeartConfig)));
    }
}
