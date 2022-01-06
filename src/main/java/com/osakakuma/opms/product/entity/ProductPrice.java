package com.osakakuma.opms.product.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductPrice {
    private String sku;
    private BigDecimal sellPrice;
    private BigDecimal costPrice;
    private BigDecimal grossMargin;
    private String remark;
    private ProductPriceStatus status;
}
