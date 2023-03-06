package com.example.nettyDemo.config.client;

import com.example.nettyDemo.config.server.NettyHttpCodingConfig;
import com.example.nettyDemo.config.server.NettyMyCodingConfig;
import com.example.nettyDemo.config.server.NettyServerHeartConfig;
import com.example.nettyDemo.config.server.NettyWebSocketCodingConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/6
 */
@ConfigurationProperties(prefix = "netty.client")
@EnableConfigurationProperties({NettyMyCodingConfig.class, NettyClientHeartConfig.class})
public class NettyClientConfig {
    private String address;
    private int port;
    private int workGroupThread;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWorkGroupThread() {
        return workGroupThread;
    }

    public void setWorkGroupThread(int workGroupThread) {
        this.workGroupThread = workGroupThread;
    }
}
