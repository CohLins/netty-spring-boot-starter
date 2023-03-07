package com.example.nettyDemo.config.server;

import com.example.nettyDemo.channel.server.NettyServerChannelInit;
import com.example.nettyDemo.channel.server.impl.NettyHttpServerChannelInit;
import com.example.nettyDemo.channel.server.impl.NettyMyCodingServerChannelInit;
import com.example.nettyDemo.channel.server.impl.NettyWebSocketServerChannelInit;
import com.example.nettyDemo.handler.NettyServerHandlerInitializer;
import com.example.nettyDemo.service.NettyServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/2/27
 */
@Configuration
@EnableConfigurationProperties({NettyServerConfig.class})
@ConditionalOnProperty(
        value = {"netty.server.enabled"},
        havingValue = "true"
)
public class NettyServerAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(NettyServerChannelInit.class)
    @ConditionalOnProperty(
            value = {"netty.server.coding-type"},
            havingValue = "http"
    )
    public NettyServerChannelInit serverHttpChannelInit(){
        return new NettyHttpServerChannelInit();
    }

    @Bean
    @ConditionalOnMissingBean(NettyServerChannelInit.class)
    @ConditionalOnProperty(
            value = {"netty.server.coding-type"},
            havingValue = "websocket"
    )
    public NettyServerChannelInit serverWebSocketChannelInit(){
        return new NettyWebSocketServerChannelInit();
    }

    @Bean
    @ConditionalOnMissingBean(NettyServerChannelInit.class)
    @ConditionalOnProperty(
            value = {"netty.server.coding-type"},
            havingValue = "my_config"
    )
    public NettyServerChannelInit serverMyConfigChannelInit(){
        return new NettyMyCodingServerChannelInit();
    }


    @Bean
    @ConditionalOnMissingBean(value = NettyServerHandlerInitializer.class)
    @ConditionalOnBean(value = NettyServerChannelInit.class)
    public NettyServerHandlerInitializer channelInitializer(NettyServerConfig nettyServerConfig, ApplicationContext context, NettyServerChannelInit nettyServerChannelInit) {
        return new NettyServerHandlerInitializer(nettyServerConfig,context, nettyServerChannelInit);
    }



    @Bean
    @ConditionalOnBean(value = NettyServerHandlerInitializer.class)
    public NettyServer nettyServer(NettyServerConfig nettyServerConfig,NettyServerHandlerInitializer channelInitializer) {
        return new NettyServer(nettyServerConfig,channelInitializer);
    }


}
