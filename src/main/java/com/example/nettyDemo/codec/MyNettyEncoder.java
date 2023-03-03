package com.example.nettyDemo.codec;

import com.alibaba.fastjson.JSON;

import com.example.nettyDemo.codec.domain.NettyMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2022/8/10
 */
@Slf4j
public class MyNettyEncoder extends MessageToByteEncoder<NettyMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyMsg msg, ByteBuf out) throws Exception {
        // 写入开头的标志
        out.writeShort(msg.getMsgHead().getStartSign());
        // 写入秒时间戳
        out.writeInt(msg.getMsgHead().getTimeStamp());

        byte[] bytes = JSON.toJSON(msg.getNettyBody()).toString().getBytes();
        // 写入消息长度
        out.writeInt(bytes.length);
        // 写入消息主体
        out.writeBytes(bytes);
    }
}
