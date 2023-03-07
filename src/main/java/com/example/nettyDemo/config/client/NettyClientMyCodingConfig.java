package com.example.nettyDemo.config.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/1
 */
@ConfigurationProperties(prefix = "netty.client.my-config")
public class NettyClientMyCodingConfig {
    private int maxFrameLength=1024;
    private int lengthFieldOffset;
    private int lengthFieldLength;
    private int lengthAdjustment;
    private int initialBytesToStrip;

    public int getMaxFrameLength() {
        return maxFrameLength;
    }

    public void setMaxFrameLength(int maxFrameLength) {
        this.maxFrameLength = maxFrameLength;
    }

    public int getLengthFieldOffset() {
        return lengthFieldOffset;
    }

    public void setLengthFieldOffset(int lengthFieldOffset) {
        this.lengthFieldOffset = lengthFieldOffset;
    }

    public int getLengthFieldLength() {
        return lengthFieldLength;
    }

    public void setLengthFieldLength(int lengthFieldLength) {
        this.lengthFieldLength = lengthFieldLength;
    }

    public int getLengthAdjustment() {
        return lengthAdjustment;
    }

    public void setLengthAdjustment(int lengthAdjustment) {
        this.lengthAdjustment = lengthAdjustment;
    }

    public int getInitialBytesToStrip() {
        return initialBytesToStrip;
    }

    public void setInitialBytesToStrip(int initialBytesToStrip) {
        this.initialBytesToStrip = initialBytesToStrip;
    }
}
