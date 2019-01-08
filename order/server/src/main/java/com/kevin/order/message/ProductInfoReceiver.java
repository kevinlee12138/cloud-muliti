package com.kevin.order.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kevin.order.utils.JsonUtil;
import com.kevin.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductInfoReceiver {
    private static final String PRODUCT_STOCK_TEMPLTE="product_stock_%s";

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("productInfo"),//交换器
            key = "decreaseProduct",       //roukey
            value = @Queue("myProduct")   //对列名
    ))
    public void processDigitalMsq(String message){

        List<ProductInfoOutput> productInfoOutputList = (List<ProductInfoOutput>)JsonUtil.fromJson(message,
                new TypeReference<List<ProductInfoOutput>>() {});
        log.info("productInfoOutputList:{}",productInfoOutputList);

        for(ProductInfoOutput productInfoOutput:productInfoOutputList){
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLTE,productInfoOutput.getProductId()),
                    String.valueOf(productInfoOutput.getProductStock()));
        }
    }
}
