package com.example.nettyDemo.annotation;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Component
@Scope("prototype")
public @interface NettyClientHandler {
    public int order() default 0;
}
