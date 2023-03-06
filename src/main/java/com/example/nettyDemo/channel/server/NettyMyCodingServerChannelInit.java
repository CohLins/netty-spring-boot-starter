package com.example.nettyDemo.channel.server;

import com.example.nettyDemo.NettyHandlerCollect;
import com.example.nettyDemo.codec.MyNettyDecoder;
import com.example.nettyDemo.codec.MyNettyEncoder;
import com.example.nettyDemo.config.server.NettyServerConfig;
import com.example.nettyDemo.handler.server.NettyServerHeartbeatHandler;
import io.netty.channel.Channel;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/6
 */
public class NettyMyCodingServerChannelInit implements NettyServerChannelInit {

    @Override
    public void initChannelHandlers(Channel channel, NettyServerConfig nettyServerConfig, ApplicationContext context) {
        channel.pipeline().addLast(new MyNettyDecoder(
                nettyServerConfig.getMyConfig().getMaxFrameLength(),
                nettyServerConfig.getMyConfig().getLengthFieldOffset(),
                nettyServerConfig.getMyConfig().getLengthFieldLength(),
                nettyServerConfig.getMyConfig().getLengthAdjustment(),
                nettyServerConfig.getMyConfig().getInitialBytesToStrip())
        );
        channel.pipeline().addLast(new MyNettyEncoder());
        // 心跳机制
        channel.pipeline().addLast(new NettyServerHeartbeatHandler(nettyServerConfig,context));
        // 自定义handler添加
        List<NettyHandlerCollect> channelHandlers = getChannelHandlers();
        channelHandlers.forEach(item -> {
            channel.pipeline().addLast(item.getChannelHandler());
        });
    }
}
