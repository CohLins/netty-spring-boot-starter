package com.example.nettyDemo.config;

import com.example.nettyDemo.handler.NettyServerHandlerInitializer;
import com.example.nettyDemo.service.NettyServer;
import io.netty.channel.ChannelInitializer;
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
    @ConditionalOnMissingBean(value = ChannelInitializer.class)
    public ChannelInitializer channelInitializer(NettyServerConfig nettyServerConfig, ApplicationContext context) {
        return new NettyServerHandlerInitializer(nettyServerConfig,context);
    }



    @Bean
    @ConditionalOnBean(value = ChannelInitializer.class)
    public NettyServer nettyServer(NettyServerConfig nettyServerConfig,ChannelInitializer channelInitializer) {
        return new NettyServer(nettyServerConfig,channelInitializer);
    }


}
