package com.osakakuma.opms.product.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductImage {
    private String sku;
    private String fileId;
    private BigDecimal height;
    private BigDecimal width;
    private String path;
    private int seq;
}
