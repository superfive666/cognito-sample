package com.osakakuma.opms.product.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.osakakuma.opms.product.entity.ProductImage;
import com.osakakuma.opms.product.entity.ProductMasterStatus;
import com.osakakuma.opms.product.entity.ProductPriceStatus;
import com.osakakuma.opms.product.entity.ProductUrl;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductRecord {
    private String sku;
    private String category;
    private String subCategory;
    private String brand;
    private String fourQuadrant;
    private String hsCode;
    private String hsCodeDesc;
    private String specs;
    private BigDecimal weight;
    private Instant created;
    private String createdBy;
    private Instant updated;
    private String updatedBy;
    private ProductMasterStatus status;
    private Instant approved;
    private String approvedBy;
    private String nameEn;
    private String nameJp;
    private String nameZh;
    private String functionEn;
    private String functionJp;
    private String functionZh;
    private String instructionEn;
    private String instructionJp;
    private String instructionZh;
    private String cautionEn;
    private String cautionJp;
    private String cautionZh;
    private String ingredientEn;
    private String ingredientJp;
    private String ingredientZh;
    private String originEn;
    private String originJp;
    private String originZh;
    private String manufacturerAddrEn;
    private String manufacturerAddrJp;
    private String manufacturerAddrZh;
    private BigDecimal sellPrice;
    private BigDecimal costPrice;
    private BigDecimal grossMargin;
    private String remark;
    private ProductPriceStatus priceStatus;
    private List<ProductImage> images;
    private List<ProductUrl> urls;
}
