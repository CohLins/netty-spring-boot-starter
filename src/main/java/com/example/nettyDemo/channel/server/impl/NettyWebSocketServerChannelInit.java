package com.example.nettyDemo.channel.server.impl;

import com.example.nettyDemo.NettyHandlerCollect;
import com.example.nettyDemo.channel.server.NettyServerChannelInit;
import com.example.nettyDemo.config.server.NettyServerConfig;
import com.example.nettyDemo.handler.server.NettyServerHeartbeatHandler;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/6
 */
public class NettyWebSocketServerChannelInit implements NettyServerChannelInit {

    @Override
    public void initChannelHandlers(Channel channel, NettyServerConfig nettyServerConfig, ApplicationContext context) {
        channel.pipeline().addLast(nettyServerConfig.getHttpConfig().getMaxInitialLineLength() == 0 ? new HttpServerCodec() :
                new HttpServerCodec(nettyServerConfig.getHttpConfig().getMaxInitialLineLength(),
                        nettyServerConfig.getHttpConfig().getMaxHeaderSize(),
                        nettyServerConfig.getHttpConfig().getMaxChunkSize(),
                        nettyServerConfig.getHttpConfig().isValidateHeaders(),
                        nettyServerConfig.getHttpConfig().getInitialBufferSize()));
        channel.pipeline().addLast(new ChunkedWriteHandler());
        channel.pipeline().addLast(new HttpObjectAggregator(nettyServerConfig.getHttpConfig().getMaxContentLength()));
        channel.pipeline().addLast(new WebSocketServerProtocolHandler(nettyServerConfig.getWebSocketConfig().getWebsocketPath(),
                nettyServerConfig.getWebSocketConfig().getSubprotocols(),
                nettyServerConfig.getWebSocketConfig().isAllowExtensions(),
                nettyServerConfig.getWebSocketConfig().getMaxFrameSize(),
                nettyServerConfig.getWebSocketConfig().isAllowMaskMismatch(),
                nettyServerConfig.getWebSocketConfig().isCheckStartsWith()));
        // 心跳机制
        channel.pipeline().addLast(new NettyServerHeartbeatHandler(nettyServerConfig, context));
        // 自定义handler添加
        List<NettyHandlerCollect> channelHandlers = getChannelHandlers();
        channelHandlers.forEach(item -> {
            channel.pipeline().addLast(item.getChannelHandler());
        });
    }
}
