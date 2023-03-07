package com.example.nettyDemo.entity;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/7
 */
public class NettyIdleEntity {
    private ChannelHandlerContext ctx;
    private IdleStateEvent evt;

    public NettyIdleEntity(ChannelHandlerContext ctx, IdleStateEvent evt){
        this.ctx=ctx;
        this.evt=evt;
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
}
