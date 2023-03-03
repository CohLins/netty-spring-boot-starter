package com.example.nettyDemo.codec.domain;



import com.alibaba.fastjson.JSON;

import com.example.nettyDemo.codec.ServiceCodeEnum;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class NettyBody {
    // 通信凭证
    private String token;

    // 消息ID
    private String msgId;

    // 消息类型
    private short msgType;

    // 消息 这里序列化采用JSON序列化
    // 所以这个msg可以是实体类的msg 两端通过消息类型来判断实体类类型
    private String msg;

    public NettyBody(){

    }
    public NettyBody(ServiceCodeEnum codeEnum, Object msg){
        this.token=""; // 鉴权使用
        this.msgId=""; // 拓展使用
        this.msgType=codeEnum.getCode();
        this.msg= JSON.toJSONString(msg);
    }
}
