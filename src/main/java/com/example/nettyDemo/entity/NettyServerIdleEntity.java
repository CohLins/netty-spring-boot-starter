package com.example.nettyDemo.entity;

import com.example.nettyDemo.config.NettyServerHeartConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/2/28
 */
public class NettyServerIdleEntity {
    private ChannelHandlerContext ctx;
    private IdleStateEvent evt;
    private NettyServerHeartConfig serverHeartConfig;

    public NettyServerIdleEntity(ChannelHandlerContext ctx, IdleStateEvent evt, NettyServerHeartConfig serverHeartConfig){
        this.ctx=ctx;
        this.evt=evt;
        this.serverHeartConfig=serverHeartConfig;
    }


    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public IdleStateEvent getEvt() {
        return evt;
    }

    public void setEvt(IdleStateEvent evt) {
        this.evt = evt;
    }

    public NettyServerHeartConfig getServerHeartConfig() {
        return serverHeartConfig;
    }

    public void setServerHeartConfig(NettyServerHeartConfig serverHeartConfig) {
        this.serverHeartConfig = serverHeartConfig;
    }
}
