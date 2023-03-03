package com.example.nettyDemo.codec.domain;




import com.example.nettyDemo.codec.ServiceCodeEnum;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class NettyMsg {
    private NettyMsgHead msgHead=new NettyMsgHead();
    private NettyBody nettyBody;

    public NettyMsg(ServiceCodeEnum codeEnum, Object msg){
        this.nettyBody=new NettyBody(codeEnum, msg);
    }
}
