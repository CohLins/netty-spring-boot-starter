package com.example.nettyDemo.event;

import com.example.nettyDemo.entity.NettyServerNettyIdleEntity;
import org.springframework.context.ApplicationEvent;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/2/28
 */
public class NettyServerIdleEvent extends ApplicationEvent {

    public NettyServerIdleEvent(NettyServerNettyIdleEntity nettyServerIdleEntity) {
        super(nettyServerIdleEntity);
    }
}
