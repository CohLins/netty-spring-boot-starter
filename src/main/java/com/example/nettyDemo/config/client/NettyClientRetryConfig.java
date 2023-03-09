package com.example.nettyDemo.config.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/9
 */
@ConfigurationProperties(prefix = "netty.client.retry")
public class NettyClientRetryConfig {
    private int retryMaxNum=5;
    private int retryBaseTime=4;
    private int retryMaxTime=30;

    public int getRetryMaxNum() {
        return retryMaxNum;
    }

    public void setRetryMaxNum(int retryMaxNum) {
        this.retryMaxNum = retryMaxNum;
    }

    public int getRetryBaseTime() {
        return retryBaseTime;
    }

    public void setRetryBaseTime(int retryBaseTime) {
        this.retryBaseTime = retryBaseTime;
    }

    public int getRetryMaxTime() {
        return retryMaxTime;
    }

    public void setRetryMaxTime(int retryMaxTime) {
        this.retryMaxTime = retryMaxTime;
    }
}
