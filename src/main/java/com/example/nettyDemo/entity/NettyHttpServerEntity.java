package com.example.nettyDemo.entity;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/2
 */
public class NettyHttpServerEntity {
    private ChannelHandlerContext ctx;
    private Channel channel;
    private FullHttpRequest request;

    public NettyHttpServerEntity(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        this.request = request;
        this.channel = channel;
        this.ctx = ctx;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public FullHttpRequest getRequest() {
        return request;
    }

    public void setRequest(FullHttpRequest request) {
        this.request = request;
    }
}
