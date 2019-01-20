package com.kevin.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@DefaultProperties(defaultFallback = "defaultFallBack")
public class HystrixController {

   // @HystrixCommand(fallbackMethod = "fallBack")
    @HystrixCommand
    @GetMapping("/getProductInfoList")
    public String getProductInfoList(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://127.0.0.1:8081/product/listForOrder",
                Arrays.asList("157875196366160022"),String.class);
    }

    public String fallBack(){
        return "太拥挤了，请稍后重试-----";
    }

    public String defaultFallBack(){
        return "默认提示；太拥挤了，请稍后重试-----";
    }
}
