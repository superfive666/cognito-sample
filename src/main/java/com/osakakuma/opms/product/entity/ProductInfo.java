package com.osakakuma.opms.product.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductInfo {
    private String sku;
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
}
