package com.kevin.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@DefaultProperties(defaultFallback = "defaultFallBack")
public class HystrixController {

   // @HystrixCommand(fallbackMethod = "fallBack")
    //超时设置
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
//    })

    //熔断设置
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),  //设置熔断
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), //熔断时间窗口
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),      //重试次数
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")   //错误百分比
    })
    @GetMapping("/getProductInfoList")
    public String getProductInfoList(@RequestParam Integer number){
        if (number % 2 == 0){
            return "ok";
        }

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
