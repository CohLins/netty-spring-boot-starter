package com.example.nettyDemo.config;

import com.example.nettyDemo.NettyCodingTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/2/27
 */
@ConfigurationProperties(prefix = "netty.server")
@EnableConfigurationProperties({NettyMyCodingConfig.class, NettyHttpCodingConfig.class, NettyServerHeartConfig.class})
public class NettyServerConfig {

    private int port = 11111;

    private int bossGroupThread;

    private int workGroupThread;

    private NettyCodingTypeEnum codingType = NettyCodingTypeEnum.DEFINITION;

    private NettyMyCodingConfig myConfig;

    private NettyHttpCodingConfig httpConfig;

    private NettyServerHeartConfig heartConfig;

    public NettyServerConfig(NettyMyCodingConfig myConfig, NettyHttpCodingConfig httpConfig, NettyServerHeartConfig heartConfig) {
        this.myConfig = myConfig;
        this.httpConfig = httpConfig;
        this.heartConfig = heartConfig;
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBossGroupThread() {
        return bossGroupThread;
    }

    public void setBossGroupThread(int bossGroupThread) {
        this.bossGroupThread = bossGroupThread;
    }

    public int getWorkGroupThread() {
        return workGroupThread;
    }

    public void setWorkGroupThread(int workGroupThread) {
        this.workGroupThread = workGroupThread;
    }

    public NettyMyCodingConfig getMyConfig() {
        return myConfig;
    }

    public void setMyConfig(NettyMyCodingConfig myConfig) {
        this.myConfig = myConfig;
    }

    public NettyHttpCodingConfig getHttpConfig() {
        return httpConfig;
    }

    public void setHttpConfig(NettyHttpCodingConfig httpConfig) {
        this.httpConfig = httpConfig;
    }

    public NettyCodingTypeEnum getCodingType() {
        return codingType;
    }

    public void setCodingType(NettyCodingTypeEnum codingType) {
        this.codingType = codingType;
    }

    public NettyServerHeartConfig getHeartConfig() {
        return heartConfig;
    }

    public void setHeartConfig(NettyServerHeartConfig heartConfig) {
        this.heartConfig = heartConfig;
    }
}
