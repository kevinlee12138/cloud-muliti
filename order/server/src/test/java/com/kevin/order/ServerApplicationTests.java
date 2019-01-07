package com.kevin.order;

import com.kevin.order.message.StreamClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerApplicationTests {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    StreamClient streamClient;

    @Test
    public void contextLoads() {
        amqpTemplate.convertAndSend("myQueue","now:"+new Date());
    }

    @Test
    public void testDigital() {
        amqpTemplate.convertAndSend("myOrder","digital","digital:"+new Date());
    }

    @Test
    public void testFood() {
        amqpTemplate.convertAndSend("myOrder","food","food:"+new Date());
    }

    @Test
    public void testSendStreamMsg(){
        String message = "now:" +new Date();
        streamClient.output().send(MessageBuilder.withPayload(message).build());
    }
}

