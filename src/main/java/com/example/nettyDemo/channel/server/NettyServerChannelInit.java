package com.example.nettyDemo.channel.server;

import cn.hutool.core.collection.CollectionUtil;
import com.example.nettyDemo.NettyHandlerCollect;
import com.example.nettyDemo.utils.SpringUtils;
import com.example.nettyDemo.annotation.NettyServerHandler;
import com.example.nettyDemo.config.server.NettyServerConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface NettyServerChannelInit {

    default void addChannelHandlers(Channel channel) {
        List<NettyHandlerCollect> handlers = new ArrayList<>();
        Map<String, ChannelHandler> beans = SpringUtils.getBeans(ChannelHandler.class);

        if (CollectionUtil.isNotEmpty(beans)) {
            beans.forEach((k, v) -> {
                NettyServerHandler annotation = v.getClass().getAnnotation(NettyServerHandler.class);
                if (annotation != null) {
                    handlers.add(new NettyHandlerCollect(annotation.order(), k, v));
                }
            });
        }
        Collections.sort(handlers);
        handlers.forEach(item -> {
            channel.pipeline().addLast(item.getChannelHandler());
        });
    }

    void initChannelHandlers(Channel channel, NettyServerConfig nettyServerConfig, ApplicationContext context);
}
