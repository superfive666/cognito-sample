package com.osakakuma.opms.product.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
public class ProductMaster {
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
}
