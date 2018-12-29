package com.kevin.product.repository;

import com.kevin.product.dataObject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    /**
     * 根据商品状态查找商品
     * @param productstatus
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer productstatus);

    List<ProductInfo> findByProductIdIn(List<String> productIdList);
}
