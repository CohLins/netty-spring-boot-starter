package com.example.nettyDemo.handler.server;

import com.example.nettyDemo.entity.NettyHttpServerEntity;
import com.example.nettyDemo.event.NettyHttpServerEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/1
 */
public class HttpBaseHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Resource
    private ApplicationEventPublisher applicationContext;

    public HttpBaseHandler(ApplicationEventPublisher applicationContext){
        this.applicationContext=applicationContext;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest t) throws Exception {
        applicationContext.publishEvent(new NettyHttpServerEvent(new NettyHttpServerEntity(channelHandlerContext,channelHandlerContext.channel(),t)));
    }
}
