package com.osakakuma.opms.product.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProductAuxiliaryMapper {

    Boolean skuExists(String sku);
}
