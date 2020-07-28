package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.HelloRibbonService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloRibbonController {

    @Autowired
    private HelloRibbonService helloRibbonService;

    @RequestMapping("/hi")
    public String helloRibbon(String name){
        return helloRibbonService.helloRibbon(name);
    }
}

