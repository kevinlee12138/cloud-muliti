package com.kevin.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqReceiver {

    //@RabbitListener(queues = "myQueue") 队列必须存在
    //@RabbitListener(queuesToDeclare = @Queue("myQueue"))
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue("myQueue"),
//            exchange = @Exchange("myExchange")
//    ))
//    public void processMsq(String message){
//        log.info("MqReceiver:{}",message);
//    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("digitalOrder"),
            key = "digital",
            exchange = @Exchange("myOrder")
    ))
    public void processDigitalMsq(String message){
        log.info("DigitalMsqReceiver:{}",message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("foodOrder"),
            key = "food",
            exchange = @Exchange("myOrder")
    ))
    public void processFoodMsq(String message){
        log.info("FoodMsqReceiver:{}",message);
    }

}
