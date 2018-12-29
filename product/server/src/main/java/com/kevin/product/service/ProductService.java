package com.kevin.product.service;


import com.kevin.product.common.ProductInfoOutput;
import com.kevin.product.dataObject.ProductInfo;
import com.kevin.product.dto.DecreaseStockInput;

import java.util.List;

/**
 * Created by 廖师兄
 * 2017-12-09 21:57
 */
public interface ProductService {

    /**
     * 查询所有在架商品列表
     */
    List<ProductInfo> findUpAll();

    List<ProductInfoOutput> findList(List<String> productIdList);

    void decreaseStock(List<DecreaseStockInput> cartDtoList);
}
