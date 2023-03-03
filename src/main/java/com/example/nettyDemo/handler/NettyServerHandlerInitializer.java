package com.example.nettyDemo.handler;


import cn.hutool.core.collection.CollectionUtil;
import com.example.nettyDemo.NettyHandlerCollect;
import com.example.nettyDemo.NettyServerTestHandler;
import com.example.nettyDemo.SpringUtils;
import com.example.nettyDemo.annotation.NettyServerHandler;
import com.example.nettyDemo.codec.MyNettyDecoder;
import com.example.nettyDemo.codec.MyNettyEncoder;
import com.example.nettyDemo.config.NettyServerConfig;
import com.example.nettyDemo.handler.server.HttpBaseHandler;
import com.example.nettyDemo.handler.server.NettyServerHeartbeatHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class NettyServerHandlerInitializer extends ChannelInitializer<Channel>{

    private static final Logger log = LoggerFactory.getLogger(NettyServerHandlerInitializer.class);

    private NettyServerConfig nettyServerConfig;

    private ApplicationEventPublisher context;


    public NettyServerHandlerInitializer(NettyServerConfig nettyServerConfig, ApplicationEventPublisher context) {
        this.context = context;
        this.nettyServerConfig = nettyServerConfig;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        // 协议选择
        switch (nettyServerConfig.getCodingType()) {
            case HTTP:
                httpCoding(channel);
                break;
            case WEBSOCKET:
                break;
            case MY_CONFIG:
                myCoding(channel);
                break;
        }
    }

    private void httpCoding(Channel channel) {
        channel.pipeline().addLast(nettyServerConfig.getHttpConfig().getMaxInitialLineLength() == 0 ? new HttpRequestDecoder() :
                new HttpRequestDecoder(nettyServerConfig.getHttpConfig().getMaxInitialLineLength(),
                        nettyServerConfig.getHttpConfig().getMaxHeaderSize(),
                        nettyServerConfig.getHttpConfig().getMaxChunkSize(),
                        nettyServerConfig.getHttpConfig().isValidateHeaders(),
                        nettyServerConfig.getHttpConfig().getInitialBufferSize()));
        channel.pipeline().addLast(new HttpResponseEncoder());
        channel.pipeline().addLast(new HttpObjectAggregator(nettyServerConfig.getHttpConfig().getMaxContentLength()));
        channel.pipeline().addLast(new HttpBaseHandler(context));
    }

    private void myCoding(Channel channel) {
        channel.pipeline().addLast(new MyNettyDecoder(
                nettyServerConfig.getMyConfig().getMaxFrameLength(),
                nettyServerConfig.getMyConfig().getLengthFieldOffset(),
                nettyServerConfig.getMyConfig().getLengthFieldLength(),
                nettyServerConfig.getMyConfig().getLengthAdjustment(),
                nettyServerConfig.getMyConfig().getInitialBytesToStrip())
        );
        channel.pipeline().addLast(new MyNettyEncoder());
        // 自定义handler添加
        List<ChannelHandler> handlers = getChannelHandlers();
        handlers.forEach(item->{
            channel.pipeline().addLast(item);
        });

        // 心跳机制
        channel.pipeline().addLast(new NettyServerHeartbeatHandler(nettyServerConfig,context));
    }

    private List<ChannelHandler> getChannelHandlers(){
        List<ChannelHandler> handlers=new ArrayList<>();
        Map<String, ChannelHandler> beans = SpringUtils.getBeans(ChannelHandler.class);

        if (CollectionUtil.isNotEmpty(beans)) {
            beans.forEach((k,v)->{
                NettyServerHandler annotation = v.getClass().getAnnotation(NettyServerHandler.class);
                if (annotation != null) {
                    handlers.add(v);
                }
            });
        }
        return handlers;
    }

}
