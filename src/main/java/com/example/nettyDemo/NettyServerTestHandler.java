package com.example.nettyDemo;

import com.alibaba.fastjson.JSON;

import com.example.nettyDemo.annotation.NettyServerHandler;
import com.example.nettyDemo.codec.ServiceCodeEnum;
import com.example.nettyDemo.codec.domain.NettyBody;
import com.example.nettyDemo.codec.domain.NettyMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.SocketAddress;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/1/31
 */
//@NettyServerHandler
public class NettyServerTestHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(NettyServerTestHandler.class);


    // 读取信息调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 和NIO一样有缓冲区 ByteBuf就是对ByteBuffer做了一层封装
        NettyBody msg1 = (NettyBody) msg;

        // 读取数据
        log.info("客户端信息：" + JSON.toJSON(msg1));
    }

    // 连接事件 连接成功调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        log.info(socketAddress + " 已连接");

        // 发送数据
        ctx.writeAndFlush(new NettyMsg(ServiceCodeEnum.TEST_TYPE,"Hello Client!"));
    }

    // 断开连接调用
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().remoteAddress() + " 已断开连接");
    }

    // 读取信息完成事件  信息读取完成后调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    // 异常处理  发生异常调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("全局异常信息 ex={}", cause.getMessage(), cause);
        ctx.close();
    }
}
