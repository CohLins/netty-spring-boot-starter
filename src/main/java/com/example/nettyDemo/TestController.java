package com.example.nettyDemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author czl
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2023/2/28
 */
@RestController
public class TestController {

    @GetMapping("test")
    public String test(){
        return "test";
    }
}
