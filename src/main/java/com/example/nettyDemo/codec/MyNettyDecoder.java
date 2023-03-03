package com.example.nettyDemo.codec;


import com.alibaba.fastjson.JSON;

import com.example.nettyDemo.codec.domain.NettyBody;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteOrder;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2022/8/10
 */
@Slf4j
public class MyNettyDecoder extends LengthFieldBasedFrameDecoder {
    // 开始标记
    private final short HEAD_START = (short) 0xFFFF;


    public MyNettyDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    public MyNettyDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    public MyNettyDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    public MyNettyDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 经过父解码器的处理 我们就不需要在考虑沾包和半包了
        // 当然，想要自己处理沾包和半包问题也不是不可以
        ByteBuf decode = (ByteBuf) super.decode(ctx, in);
        if (decode == null) {
            return null;
        }
        // 开始标志校验  开始标志不匹配直接 过滤此条消息
        short startIndex = decode.readShort();
        if (startIndex != HEAD_START) {
            return null;
        }
        // 时间戳
        int timeIndex = decode.readInt();
        // 消息体长度
        int lenOfBody = decode.readInt();
        // 读取消息
        byte[] msgByte = new byte[lenOfBody];
        decode.readBytes(msgByte);
        String msgContent = new String(msgByte);
        // 将消息转成实体类 传递给下面的数据处理器
        return JSON.parseObject(msgContent, NettyBody.class);
    }
}
