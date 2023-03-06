package com.example.nettyDemo.handler;


import com.example.nettyDemo.channel.client.NettyClientChannelInit;
import com.example.nettyDemo.channel.server.NettyServerChannelInit;
import com.example.nettyDemo.config.client.NettyClientConfig;
import com.example.nettyDemo.config.server.NettyServerConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;


public class NettyClientHandlerInitializer extends ChannelInitializer<Channel> {

    private static final Logger log = LoggerFactory.getLogger(NettyClientHandlerInitializer.class);

    private NettyClientConfig nettyClientConfig;

    private ApplicationContext context;

    private NettyClientChannelInit nettyClientChannelInit;

    public NettyClientHandlerInitializer(NettyClientConfig nettyClientConfig, ApplicationContext context, NettyClientChannelInit nettyClientChannelInit) {
        this.context=context;
        this.nettyClientConfig=nettyClientConfig;
        this.nettyClientChannelInit=nettyClientChannelInit;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {

    }
}
