package com.example.nettyDemo.codec.domain;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

@Data
public class NettyMsgHead {
    // 开始标识
    private short startSign = (short) 0xFFFF;
    // 时间戳
    private final int timeStamp;

    public NettyMsgHead(){
        this.timeStamp=(int)(DateUtil.current() / 1000);
    }
}
