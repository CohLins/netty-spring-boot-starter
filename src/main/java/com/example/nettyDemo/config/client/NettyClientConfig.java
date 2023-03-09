package com.example.nettyDemo.config.client;

import com.example.nettyDemo.channel.client.NettyClientCodingTypeEnum;
import com.example.nettyDemo.config.server.NettyHttpCodingConfig;
import com.example.nettyDemo.config.server.NettyServerHeartConfig;
import com.example.nettyDemo.config.server.NettyServerMyCodingConfig;
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
@EnableConfigurationProperties({NettyClientMyCodingConfig.class, NettyClientHeartConfig.class, NettyClientRetryConfig.class})
public class NettyClientConfig {
    private boolean enabled = false;
    private String address = "127.0.0.1";
    private int port = 11111;
    private int workGroupThread;
    private NettyClientCodingTypeEnum codingType = NettyClientCodingTypeEnum.DEFAULT;

    private NettyClientMyCodingConfig myConfig;
    private NettyClientHeartConfig heartConfig;
    private NettyClientRetryConfig retryConfig;

    public NettyClientConfig(NettyClientMyCodingConfig myConfig, NettyClientHeartConfig heartConfig, NettyClientRetryConfig retryConfig) {
        this.myConfig = myConfig;
        this.heartConfig = heartConfig;
        this.retryConfig = retryConfig;
    }

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

    public NettyClientMyCodingConfig getMyConfig() {
        return myConfig;
    }

    public void setMyConfig(NettyClientMyCodingConfig myConfig) {
        this.myConfig = myConfig;
    }

    public NettyClientHeartConfig getHeartConfig() {
        return heartConfig;
    }

    public void setHeartConfig(NettyClientHeartConfig heartConfig) {
        this.heartConfig = heartConfig;
    }

    public NettyClientRetryConfig getRetryConfig() {
        return retryConfig;
    }

    public void setRetryConfig(NettyClientRetryConfig retryConfig) {
        this.retryConfig = retryConfig;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public NettyClientCodingTypeEnum getCodingType() {
        return codingType;
    }

    public void setCodingType(NettyClientCodingTypeEnum codingType) {
        this.codingType = codingType;
    }
}
