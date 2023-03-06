package com.example.nettyDemo;

import com.example.nettyDemo.annotation.NettyServerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/3/6
 */
@NettyServerHandler
public class NettyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final static Logger log= LoggerFactory.getLogger(NettyWebSocketHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("建立连接");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        log.info("收到消息："+textWebSocketFrame.text());
        channelHandlerContext.writeAndFlush(new TextWebSocketFrame(textWebSocketFrame.text()));
    }
}
