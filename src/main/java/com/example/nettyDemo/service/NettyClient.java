package com.example.nettyDemo.service;

import com.example.nettyDemo.config.client.NettyClientConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PreDestroy;


@Slf4j
public class NettyClient implements InitializingBean {

    private EventLoopGroup group = null;

    private NettyClientConfig nettyClientConfig;

    private ChannelInitializer channelInitializer;


    private int currentRetryNum = 0;

    private int currentRetryTime;

    public NettyClient(NettyClientConfig nettyClientConfig, ChannelInitializer channelInitializer) {
        this.nettyClientConfig = nettyClientConfig;
        this.channelInitializer = channelInitializer;
        currentRetryTime = nettyClientConfig.getRetryConfig().getRetryBaseTime();
    }

    public void nettyClientStart() {
        group = nettyClientConfig.getWorkGroupThread() < 0 ? new NioEventLoopGroup() : new NioEventLoopGroup(nettyClientConfig.getWorkGroupThread());
        try {

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
            future.addListener(event -> {
                if (event.isSuccess()) {
                    currentRetryNum = 1;
                    currentRetryTime = nettyClientConfig.getRetryConfig().getRetryBaseTime();
                    log.info("连接 Netty Server 成功,地址：{},端口：{},协议:{}", nettyClientConfig.getAddress(), nettyClientConfig.getPort(), null);
                } else {
                    currentRetryNum += 1;
                    log.error("Netty Client Connect Fail");
                }
            });
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("Netty Client start error：{},{}", e.getMessage(), e);
        } finally {
            group.shutdownGracefully();
            if (currentRetryNum <= nettyClientConfig.getRetryConfig().getRetryMaxNum()) {
                currentRetryTime = currentRetryNum == 1 ? currentRetryTime : currentRetryTime << 1;
                currentRetryTime = currentRetryTime > nettyClientConfig.getRetryConfig().getRetryMaxTime() ?
                        nettyClientConfig.getRetryConfig().getRetryMaxTime() : currentRetryTime;
                log.warn("Netty Client Retry Connect start,num:{},time:{}s ", currentRetryNum, currentRetryTime);
                try {
                    Thread.sleep(currentRetryTime * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                nettyClientStart();
            }
        }
    }

    @PreDestroy
    public void destroy() {
        // 优雅的关闭 释放资源
        if (group != null) {
            group.shutdownGracefully();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(() -> {
            nettyClientStart();
        }).start();
    }
}
