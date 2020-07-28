package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloFeignController {

    @Autowired
    private HelloFeignService helloFeignService;

    @RequestMapping("/hi")
    public String helloFeign(String name){
        return helloFeignService.helloFeign(name);
    }


}
