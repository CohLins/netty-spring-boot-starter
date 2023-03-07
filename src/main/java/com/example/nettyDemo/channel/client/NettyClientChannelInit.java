package com.example.nettyDemo.channel.client;

import cn.hutool.core.collection.CollectionUtil;
import com.example.nettyDemo.NettyHandlerCollect;
import com.example.nettyDemo.utils.SpringUtils;
import com.example.nettyDemo.annotation.NettyClientHandler;
import com.example.nettyDemo.config.client.NettyClientConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface NettyClientChannelInit {

    default List<NettyHandlerCollect> getChannelHandlers() {
        List<NettyHandlerCollect> handlers = new ArrayList<>();
        Map<String, ChannelHandler> beans = SpringUtils.getBeans(ChannelHandler.class);

        if (CollectionUtil.isNotEmpty(beans)) {
            beans.forEach((k, v) -> {
                NettyClientHandler annotation = v.getClass().getAnnotation(NettyClientHandler.class);
                if (annotation != null) {
                    handlers.add(new NettyHandlerCollect(annotation.order(), k, v));
                }
            });
        }
        Collections.sort(handlers);
        return handlers;
    }

    void initChannelHandlers(Channel channel, NettyClientConfig nettyClientConfig, ApplicationContext context);
}
