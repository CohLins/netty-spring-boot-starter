package com.example.nettyDemo;

import com.alibaba.fastjson.JSON;
import com.example.nettyDemo.annotation.NettyServerHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.Resource;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/1
 */
//@NettyServerHandler
public class HttpBaseHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final static Logger log= LoggerFactory.getLogger(HttpBaseHandler.class);


    @Resource
    private ApplicationEventPublisher applicationContext;

    public HttpBaseHandler(ApplicationEventPublisher applicationContext){
        this.applicationContext=applicationContext;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest t) throws Exception {
        log.info("http请求 uri：{}，method：{}",t.uri(),t.method());

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.content().writeBytes(JSON.toJSONBytes("Hello"));
        response.headers().add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON+";charset=UTF-8");
        response.headers().add(HttpHeaderNames.CONTENT_LENGTH,response.content().readableBytes());
        channelHandlerContext.writeAndFlush(response);
    }
}
