package com.kevin.order.controller;


import com.kevin.order.dataobject.ProductInfo;
import com.kevin.product.client.ProductClient;
import com.kevin.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class TestProductController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getMsg")
    public String getMsg(){
        String msg = restTemplate.getForObject("http://localhost:8081/prod/msg",String.class);
        log.info("msg={}",msg);
        return msg;
    }

//    @GetMapping("/getMsg1")
//    public String getMsg1(){
//        String msg = productClient.getMsg();
//        log.info("msg={}",msg);
//        return msg;
//    }

    @GetMapping("/getProductList")
    public String getProductList(){
        List<ProductInfoOutput> productInfos =  productClient.listForOrder(Arrays.asList("157875196366160022","157875227953464068"));
        log.info("product={}",productInfos);
        return "ok";
    }
}
