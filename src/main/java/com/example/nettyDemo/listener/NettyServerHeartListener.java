package com.example.nettyDemo.listener;

import com.example.nettyDemo.entity.NettyServerIdleEntity;
import com.example.nettyDemo.event.NettyServerIdleEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/3
 */
@Component
public class NettyServerHeartListener {
    private static final Logger log=LoggerFactory.getLogger(NettyServerHeartListener.class);

    @EventListener(NettyServerIdleEvent.class)
    public void serverIdleHandler(NettyServerIdleEvent event){
        NettyServerIdleEntity source = (NettyServerIdleEntity) event.getSource();
        log.info("心跳事件触发："+ source.getEvt().state());
    }
}
