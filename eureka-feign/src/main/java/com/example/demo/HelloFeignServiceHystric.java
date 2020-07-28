package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class HelloFeignServiceHystric implements  HelloFeignService{
    @Override
    public String helloFeign(String name) {
        return "hi," + name + ",断路机制启动，hiServiceError";
    }
}
