package com.example.nettyDemo.handler;


import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;


public class NettyClientHandlerInitializer extends ChannelInitializer<Channel> {



    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
//                .addLast(new IdleStateHandler(0, 10, 0))
//                .addLast("decoder", new StringDecoder())
//                .addLast("encoder", new StringEncoder())
//                .addLast(new HttpServerCodec())
                .addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2))
//                .addLast(new MyNettyDecoder())
                .addLast(new LengthFieldPrepender(2));
//                .addLast(new MyNettyEncoder())
//                .addLast(new ProtobufVarint32FrameDecoder())
//                .addLast(new ProtobufDecoder(MessageBase.Message.getDefaultInstance()))
//                .addLast(new ProtobufVarint32LengthFieldPrepender())
//                .addLast(new ProtobufEncoder())
//                .addLast(new HeartbeatHandler())
//                .addLast(new LineBasedFrameDecoder(2))
//                .addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("$$".getBytes()))) // 自定义 分隔符
//                .addLast(new FixedLengthFrameDecoder(10)) // 固定长度解码器
//                .addLast(new NettyClientTestHandler());
    }
}
