package com.example.nettyDemo.handler;


import com.example.nettyDemo.channel.server.NettyServerChannelInit;
import com.example.nettyDemo.config.server.NettyServerConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class NettyServerHandlerInitializer extends ChannelInitializer<Channel> {

    private static final Logger log = LoggerFactory.getLogger(NettyServerHandlerInitializer.class);

    private NettyServerConfig nettyServerConfig;

    private ApplicationContext context;

    private NettyServerChannelInit nettyServerChannelInit;

    public NettyServerHandlerInitializer(NettyServerConfig nettyServerConfig, ApplicationContext context, NettyServerChannelInit nettyServerChannelInit) {
        this.context = context;
        this.nettyServerConfig = nettyServerConfig;
        this.nettyServerChannelInit = nettyServerChannelInit;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        nettyServerChannelInit.initChannelHandlers(channel, nettyServerConfig, context);
    }

}
