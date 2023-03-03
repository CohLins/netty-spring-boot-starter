package com.example.nettyDemo.service;

import com.example.nettyDemo.config.NettyServerConfig;

import com.example.nettyDemo.handler.NettyServerHandlerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/2/27
 */
@Slf4j
public class NettyServer implements InitializingBean {

    private NettyServerConfig nettyServerConfig;

    private NioEventLoopGroup bossGroup = null;
    private NioEventLoopGroup workerGroup = null;

    private ChannelInitializer channelInitializer;

    public NettyServer(NettyServerConfig nettyServerConfig, ChannelInitializer channelInitializer) {
        this.nettyServerConfig = nettyServerConfig;
        this.channelInitializer = channelInitializer;
    }

    protected void serverStart() throws InterruptedException {
        try {
            bossGroup = new NioEventLoopGroup(nettyServerConfig.getBossGroupThread());
            workerGroup = new NioEventLoopGroup(nettyServerConfig.getWorkGroupThread());
            // 服务端启动类
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 传入两个线程组
            bootstrap.group(bossGroup, workerGroup)
                    // 指定Channel 和NIO一样是采用Channel通道的方式通信 所以需要指定服务端通道
                    .channel(NioServerSocketChannel.class)
                    //使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(nettyServerConfig.getPort()))

                    //服务端可连接队列数,对应TCP/IP协议listen函数中backlog参数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //设置数据处理器
                    .childHandler(channelInitializer);
            // 同步等待成功
            ChannelFuture future = bootstrap.bind().sync();
            if (future.isSuccess()) {
                log.info("启动 Netty Server 成功,端口：{},协议:{}",nettyServerConfig.getPort(),nettyServerConfig.getCodingType());
            }
            future.channel().closeFuture().sync();
        }catch (Exception e){
            log.error("Netty server start error:{},{}",e.getMessage(),e);
        }finally {
            log.info("Netty Server shutdown");
            // 优雅的关闭 释放资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    @PreDestroy
    public void destroy() {
        // 优雅的关闭 释放资源
        if(bossGroup!=null){
            bossGroup.shutdownGracefully();
        }
        if(bossGroup!=null){
            bossGroup.shutdownGracefully();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(()->{
            try {
                serverStart();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
