package com.example.nettyDemo.channel.server.impl;

import com.example.nettyDemo.NettyHandlerCollect;
import com.example.nettyDemo.channel.server.NettyServerChannelInit;
import com.example.nettyDemo.config.server.NettyServerConfig;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/6
 */
public class NettyHttpServerChannelInit implements NettyServerChannelInit {

    @Override
    public void initChannelHandlers(Channel channel, NettyServerConfig nettyServerConfig, ApplicationContext context) {
        channel.pipeline().addLast(nettyServerConfig.getHttpConfig().getMaxInitialLineLength() == 0 ? new HttpRequestDecoder() :
                new HttpRequestDecoder(nettyServerConfig.getHttpConfig().getMaxInitialLineLength(),
                        nettyServerConfig.getHttpConfig().getMaxHeaderSize(),
                        nettyServerConfig.getHttpConfig().getMaxChunkSize(),
                        nettyServerConfig.getHttpConfig().isValidateHeaders(),
                        nettyServerConfig.getHttpConfig().getInitialBufferSize()));
        channel.pipeline().addLast(new HttpResponseEncoder());
        channel.pipeline().addLast(new HttpObjectAggregator(nettyServerConfig.getHttpConfig().getMaxContentLength()));
        // 自定义handler添加
        addChannelHandlers(channel);
    }
}
