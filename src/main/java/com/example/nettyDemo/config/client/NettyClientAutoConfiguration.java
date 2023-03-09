package com.example.nettyDemo.config.client;

import com.example.nettyDemo.channel.client.NettyClientChannelInit;
import com.example.nettyDemo.channel.client.impl.NettyDefaultClientChannelInit;
import com.example.nettyDemo.channel.client.impl.NettyMyCodingClientChannelInit;
import com.example.nettyDemo.handler.NettyClientHandlerInitializer;
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
    @ConditionalOnMissingBean(NettyClientChannelInit.class)
    @ConditionalOnProperty(
            value = {"netty.client.coding-type"},
            havingValue = "my_config"
    )
    public NettyClientChannelInit clientMyConfigChannelInit(){
        return new NettyMyCodingClientChannelInit();
    }

    @Bean
    @ConditionalOnMissingBean(NettyClientChannelInit.class)
    @ConditionalOnProperty(
            value = {"netty.client.coding-type"},
            havingValue = "default"
    )
    public NettyClientChannelInit clientDefaultChannelInit(){
        return new NettyDefaultClientChannelInit();
    }

    @Bean
    @ConditionalOnMissingBean(value = NettyClientHandlerInitializer.class)
    @ConditionalOnBean(value = NettyClientChannelInit.class)
    public NettyClientHandlerInitializer channelInitializer(NettyClientConfig nettyClientConfig, ApplicationContext context, NettyClientChannelInit nettyClientChannelInit) {
        return new NettyClientHandlerInitializer(nettyClientConfig,context, nettyClientChannelInit);
    }


    @Bean
    @ConditionalOnBean(value = NettyClientHandlerInitializer.class)
    public NettyClient nettyClient(NettyClientConfig nettyClientConfig, NettyClientHandlerInitializer channelInitializer) {
        return new NettyClient(nettyClientConfig,channelInitializer);
    }


}
