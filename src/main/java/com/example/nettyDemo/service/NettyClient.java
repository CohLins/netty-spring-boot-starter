package com.example.nettyDemo.service;

import com.example.nettyDemo.config.client.NettyClientConfig;
import com.example.nettyDemo.config.server.NettyServerConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;


@Slf4j
public class NettyClient implements InitializingBean {

    private EventLoopGroup group=null;

    private NettyClientConfig nettyClientConfig;

    private ChannelInitializer channelInitializer;

    public NettyClient(NettyClientConfig nettyClientConfig, ChannelInitializer channelInitializer) {
        this.nettyClientConfig = nettyClientConfig;
        this.channelInitializer = channelInitializer;
    }

    public void nettyClientStart() throws InterruptedException {
        group = new NioEventLoopGroup(nettyClientConfig.getWorkGroupThread());
        try{

            Bootstrap bootstrap = new Bootstrap();
            // 客户端不需要处理连接 所以一个线程组就够了
            bootstrap.group(group)
                    // 连接通道
                    .channel(NioSocketChannel.class)
                    .remoteAddress(nettyClientConfig.getAddress(), nettyClientConfig.getPort())
                    .option(ChannelOption.TCP_NODELAY, true)
                    // 数据处理
                    .handler(channelInitializer);

            ChannelFuture future = bootstrap.connect();
            if(future.isSuccess()){
                log.info("连接 Netty Server 成功,地址：{},端口：{},协议:{}",nettyClientConfig.getAddress(),nettyClientConfig.getPort(),null);
            }
            future.channel().closeFuture().sync();
        }catch (Exception e){
            log.error("Netty Client start error：{},{}",e.getMessage(),e);
        }finally {
            group.shutdownGracefully();
        }
    }

    @PreDestroy
    public void destroy() {
        // 优雅的关闭 释放资源
        if(group!=null){
            group.shutdownGracefully();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(()->{
            try {
                nettyClientStart();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
