package com.example.nettyDemo.entity;

import com.example.nettyDemo.config.server.NettyServerHeartConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/2/28
 */
public class NettyServerNettyIdleEntity extends NettyIdleEntity {

    private NettyServerHeartConfig serverHeartConfig;

    public NettyServerNettyIdleEntity(ChannelHandlerContext ctx, IdleStateEvent evt, NettyServerHeartConfig serverHeartConfig){
        super(ctx,evt);
        this.serverHeartConfig=serverHeartConfig;
    }


    public NettyServerHeartConfig getServerHeartConfig() {
        return serverHeartConfig;
    }

    public void setServerHeartConfig(NettyServerHeartConfig serverHeartConfig) {
        this.serverHeartConfig = serverHeartConfig;
    }
}
