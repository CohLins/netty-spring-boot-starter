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
@EnableConfigurationProperties({NettyClientMyCodingConfig.class, NettyClientHeartConfig.class})
public class NettyClientConfig {
    private String address;
    private int port;
    private int workGroupThread;
    private NettyClientCodingTypeEnum codingType;

    private NettyClientMyCodingConfig myConfig;
    private NettyClientHeartConfig heartConfig;

    public NettyClientConfig(NettyClientMyCodingConfig myConfig, NettyClientHeartConfig heartConfig) {
        this.myConfig = myConfig;
        this.heartConfig = heartConfig;
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

    public NettyClientCodingTypeEnum getCodingTypeEnum() {
        return codingType;
    }

    public void setCodingTypeEnum(NettyClientCodingTypeEnum codingTypeEnum) {
        this.codingType = codingTypeEnum;
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
}
