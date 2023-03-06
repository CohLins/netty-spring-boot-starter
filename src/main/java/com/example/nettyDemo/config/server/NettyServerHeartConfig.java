package com.example.nettyDemo.config.server;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/2/28
 */
@ConfigurationProperties(prefix = "netty.server.heart")
public class NettyServerHeartConfig {
    private int readTime;
    private int writeTime;
    private int allTime;



    public int getReadTime() {
        return readTime;
    }

    public void setReadTime(int readTime) {
        this.readTime = readTime;
    }

    public int getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(int writeTime) {
        this.writeTime = writeTime;
    }

    public int getAllTime() {
        return allTime;
    }

    public void setAllTime(int allTime) {
        this.allTime = allTime;
    }
}
