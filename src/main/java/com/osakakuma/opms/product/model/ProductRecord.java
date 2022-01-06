package com.osakakuma.opms.product.model;

import com.osakakuma.opms.product.entity.ProductMasterStatus;
import com.osakakuma.opms.product.entity.ProductPriceStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
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
    private String manufacturerAddrEn;
    private String manufacturerAddrJp;
    private String manufacturerAddrZh;
    private BigDecimal sellPrice;
    private BigDecimal costPrice;
    private BigDecimal grossMargin;
    private String remark;
    private ProductPriceStatus priceStatus;
}