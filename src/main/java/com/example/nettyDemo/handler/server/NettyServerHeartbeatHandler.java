package com.example.nettyDemo.handler.server;

import com.example.nettyDemo.config.NettyServerConfig;
import com.example.nettyDemo.config.NettyServerHeartConfig;
import com.example.nettyDemo.entity.NettyServerIdleEntity;
import com.example.nettyDemo.event.NettyServerIdleEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import java.util.concurrent.TimeUnit;

public class NettyServerHeartbeatHandler extends IdleStateHandler {

    private static final Logger log = LoggerFactory.getLogger(NettyServerHeartbeatHandler.class);

    private NettyServerHeartConfig serverHeartConfig;

    private ApplicationEventPublisher applicationContext;

    public NettyServerHeartbeatHandler(NettyServerConfig serverConfig, ApplicationEventPublisher applicationContext) {
        super(serverConfig.getHeartConfig().getReadTime(), serverConfig.getHeartConfig().getWriteTime(), serverConfig.getHeartConfig().getAllTime(), TimeUnit.SECONDS);
        this.serverHeartConfig = serverConfig.getHeartConfig();
        this.applicationContext = applicationContext;
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        applicationContext.publishEvent(new NettyServerIdleEvent(new NettyServerIdleEntity(ctx, evt, serverHeartConfig)));
    }
}
