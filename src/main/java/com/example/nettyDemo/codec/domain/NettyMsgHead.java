package com.example.nettyDemo.codec.domain;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2022/8/10
 */
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
