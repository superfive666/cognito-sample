package com.osakakuma.opms.product.dao;

import com.osakakuma.opms.product.entity.ProductImage;
import com.osakakuma.opms.product.entity.ProductInfo;
import com.osakakuma.opms.product.entity.ProductMaster;
import com.osakakuma.opms.product.entity.ProductPrice;
import com.osakakuma.opms.product.model.ProductListRequest;
import com.osakakuma.opms.product.model.ProductRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper {
    ProductMaster getProductMasterBySku(String sku);

    ProductPrice getProductPriceBySku(String sku);

    ProductInfo getProductInfoBySku(String sku);

    ProductRecord getProductRecordBySku(String sku);

    List<ProductRecord> listProductRecords(ProductListRequest request);

    void insertProductMaster(ProductMaster master);

    void insertProductInfo(ProductInfo info);

    void insertProductPrice(ProductPrice price);

    void updateProductMaster(ProductMaster master);

    void updateProductInfo(ProductInfo info);

    void updateProductPrice(ProductPrice price);

    List<ProductImage> getProductImages(String sku);

    void deleteProductImage(String sku);

    void insertProductImage(ProductImage image);


}
