package com.example.nettyDemo.config.client;

import com.example.nettyDemo.channel.client.NettyClientChannelInit;
import com.example.nettyDemo.channel.server.NettyServerChannelInit;
import com.example.nettyDemo.handler.NettyClientHandlerInitializer;
import com.example.nettyDemo.handler.NettyServerHandlerInitializer;
import com.example.nettyDemo.service.NettyClient;
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
@EnableConfigurationProperties({NettyClientConfig.class})
@ConditionalOnProperty(
        value = {"netty.client.enabled"},
        havingValue = "true"
)
public class NettyClientAutoConfiguration {



    @Bean
    @ConditionalOnMissingBean(value = NettyClientHandlerInitializer.class)
    public NettyClientHandlerInitializer channelInitializer(NettyClientConfig nettyClientConfig, ApplicationContext context, NettyClientChannelInit nettyClientChannelInit) {
        return new NettyClientHandlerInitializer(nettyClientConfig,context, nettyClientChannelInit);
    }



    @Bean
    @ConditionalOnBean(value = NettyClientHandlerInitializer.class)
    public NettyClient nettyClient(NettyClientConfig nettyClientConfig, NettyServerHandlerInitializer channelInitializer) {
        return new NettyClient(nettyClientConfig,channelInitializer);
    }


}
