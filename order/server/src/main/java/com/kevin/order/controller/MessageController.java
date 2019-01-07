package com.kevin.order.controller;

import com.kevin.order.dto.OrderDTO;
import com.kevin.order.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class MessageController {
    @Autowired
    StreamClient client;


    @GetMapping("/sendMessage")
    public void sendMessage(){
        String message = "now:" +new Date();
        client.output().send(MessageBuilder.withPayload(message).build());
    }

    @GetMapping("/sendObj")
    public void sendObj(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("123456");
        client.output().send(MessageBuilder.withPayload(orderDTO).build());
    }


}
