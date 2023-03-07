package com.example.nettyDemo.entity;

import com.example.nettyDemo.config.client.NettyClientHeartConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/2/28
 */
public class NettyClientNettyIdleEntity extends NettyIdleEntity {

    private NettyClientHeartConfig clientHeartConfig;

    public NettyClientNettyIdleEntity(ChannelHandlerContext ctx, IdleStateEvent evt, NettyClientHeartConfig clientHeartConfig){
        super(ctx,evt);
        this.clientHeartConfig=clientHeartConfig;
    }


    public NettyClientHeartConfig getClientHeartConfig() {
        return clientHeartConfig;
    }

    public void setClientHeartConfig(NettyClientHeartConfig clientHeartConfig) {
        this.clientHeartConfig = clientHeartConfig;
    }
}
