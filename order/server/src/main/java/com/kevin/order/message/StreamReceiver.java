package com.kevin.order.message;

import com.kevin.order.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableBinding(StreamClient.class)
public class StreamReceiver {

//    @StreamListener(StreamClient.OUTPUT)
//    public void processFoodMsq(Object message){
//        log.info("StreamReceiver:{}",message);
//    }

    @StreamListener(StreamClient.OUTPUT)
    @SendTo(StreamClient.OUTPUT2)
    public String processFoodMsq(OrderDTO message){
        log.info("StreamReceiver:{}",message);
        return "received";
    }

    @StreamListener(StreamClient.OUTPUT2)
    public void processFoodMsq1(Object message){
        log.info("StreamReceiver:{}",message);
    }

}
