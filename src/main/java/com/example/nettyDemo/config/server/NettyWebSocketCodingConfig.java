package com.example.nettyDemo.config.server;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/6
 */
@ConfigurationProperties(prefix = "netty.server.websocket-config")
public class NettyWebSocketCodingConfig {
    private String websocketPath="/ws";
    private String subprotocols;
    private boolean allowExtensions=false;
    private int maxFrameSize=65536;
    private boolean allowMaskMismatch=false;
    private boolean checkStartsWith=false;


    public String getWebsocketPath() {
        return websocketPath;
    }

    public void setWebsocketPath(String websocketPath) {
        this.websocketPath = websocketPath;
    }

    public String getSubprotocols() {
        return subprotocols;
    }

    public void setSubprotocols(String subprotocols) {
        this.subprotocols = subprotocols;
    }

    public boolean isAllowExtensions() {
        return allowExtensions;
    }

    public void setAllowExtensions(boolean allowExtensions) {
        this.allowExtensions = allowExtensions;
    }

    public int getMaxFrameSize() {
        return maxFrameSize;
    }

    public void setMaxFrameSize(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    public boolean isAllowMaskMismatch() {
        return allowMaskMismatch;
    }

    public void setAllowMaskMismatch(boolean allowMaskMismatch) {
        this.allowMaskMismatch = allowMaskMismatch;
    }

    public boolean isCheckStartsWith() {
        return checkStartsWith;
    }

    public void setCheckStartsWith(boolean checkStartsWith) {
        this.checkStartsWith = checkStartsWith;
    }
}
