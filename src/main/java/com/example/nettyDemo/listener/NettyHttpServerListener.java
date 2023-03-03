package com.example.nettyDemo.listener;

import com.alibaba.fastjson.JSON;
import com.example.nettyDemo.entity.NettyHttpServerEntity;
import com.example.nettyDemo.event.NettyHttpServerEvent;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/2
 */
@Component
public class NettyHttpServerListener implements ApplicationListener<NettyHttpServerEvent>{
    private static final Logger log = LoggerFactory.getLogger(NettyHttpServerListener.class);

    @Override
    public void onApplicationEvent(NettyHttpServerEvent httpServerEvent) {
        NettyHttpServerEntity httpServerEntity = (NettyHttpServerEntity) httpServerEvent.getSource();
        log.info("http请求 uri：{}，method：{}",httpServerEntity.getRequest().uri(),httpServerEntity.getRequest().method());

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.content().writeBytes(JSON.toJSONBytes("Hello"));
        response.headers().add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON+";charset=UTF-8");
        response.headers().add(HttpHeaderNames.CONTENT_LENGTH,response.content().readableBytes());
        httpServerEntity.getCtx().writeAndFlush(response);
    }
}
