package com.example.nettyDemo;

import cn.hutool.core.collection.CollectionUtil;
import io.netty.channel.ChannelHandler;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/2/28
 */
public class NettyHandlerCollect implements Comparable<NettyHandlerCollect> {
    private Integer order;
    private String beanName;
    private ChannelHandler channelHandler;

    public NettyHandlerCollect(Integer order, String beanName, ChannelHandler channelHandler) {
        this.order = order;
        this.beanName = beanName;
        this.channelHandler = channelHandler;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public ChannelHandler getChannelHandler() {
        return channelHandler;
    }

    public void setChannelHandler(ChannelHandler channelHandler) {
        this.channelHandler = channelHandler;
    }


    @Override
    public int compareTo(NettyHandlerCollect o) {
        return this.order > o.order ? 1:-1;
    }
}
