package com.osakakuma.opms.price.dao;

import com.osakakuma.opms.price.model.PriceListRequest;
import com.osakakuma.opms.product.entity.ProductPrice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PriceMapper {
    List<ProductPrice> listPrice(PriceListRequest request);
}