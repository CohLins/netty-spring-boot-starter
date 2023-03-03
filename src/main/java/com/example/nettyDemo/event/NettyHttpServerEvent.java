package com.example.nettyDemo.event;

import com.example.nettyDemo.entity.NettyHttpServerEntity;
import org.springframework.context.ApplicationEvent;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/2
 */
public class NettyHttpServerEvent extends ApplicationEvent {

    public NettyHttpServerEvent(NettyHttpServerEntity source) {
        super(source);
    }
}
