package com.kevin.product.service.impl;


import com.kevin.product.common.ProductInfoOutput;
import com.kevin.product.dataObject.ProductInfo;
import com.kevin.product.dto.DecreaseStockInput;
import com.kevin.product.enums.ProductStatusEnum;
import com.kevin.product.enums.ResultEnum;
import com.kevin.product.exception.ProductException;
import com.kevin.product.repository.ProductInfoRepository;
import com.kevin.product.service.ProductService;
import com.kevin.product.utils.JsonUtil;
import com.rabbitmq.tools.json.JSONUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by 廖师兄
 * 2017-12-09 21:59
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfoOutput> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList).stream()
                .map(e ->{
                    ProductInfoOutput output = new ProductInfoOutput();
                    BeanUtils.copyProperties(e,output);
                    return output;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputs) {
        List<ProductInfo> productInfoList = decreaseStockProcess(decreaseStockInputs);
        List<ProductInfoOutput> productInfoOutputs = productInfoList.stream()
                .map(e -> {
                    ProductInfoOutput output  = new ProductInfoOutput();
                    BeanUtils.copyProperties(e,output);
                    return output;
                }).collect(Collectors.toList());
        //发送消息 
        amqpTemplate.convertAndSend("productInfo","decreaseProduct", JsonUtil.toJson(productInfoOutputs));
    }
    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputs) {
        List<ProductInfo> productInfoList = new ArrayList<>();
        for (DecreaseStockInput decreaseStockInput:decreaseStockInputs){
            ProductInfo productInfo =  productInfoRepository.findOne(decreaseStockInput.getProductId());
            //商品不存在
//            if (!productInfoOptional.isPresent()){
//                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
//            }

            // ProductInfo productInfo = productInfoOptional.get();
            //判断库存够不够
            Integer result = productInfo.getProductStock() - decreaseStockInput.getProductQuantity();
            if (result < 0){
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }


}
