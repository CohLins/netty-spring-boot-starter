package com.example.nettyDemo.codec;



/**
 * @author Administrator
 */
public enum ServiceCodeEnum {
    TEST_TYPE((short) 0xFFF1, "测试");


    private final short code;
    private final String desc;

    ServiceCodeEnum(short code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public short getCode() {
        return code;
    }

}
