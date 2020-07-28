package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@FeignClient(name  = "service-hi",fallback=HelloFeignServiceHystric.class)
public interface HelloFeignService {
    @RequestMapping(value = "/hi")
    String helloFeign(@RequestParam(value = "name") String name);
}