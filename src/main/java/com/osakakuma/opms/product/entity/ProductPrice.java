package com.osakakuma.opms.product.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductPrice {
    @Schema(description = "The globally unique parameter", required = true)
    @NotEmpty(message = "Product SKU is required parameter")
    private String sku;
    @Schema(description = "The selling price related to this product SKU", required = true)
    @NotNull(message = "Please specify the product sell price")
    private BigDecimal sellPrice;
    @Schema(name = "The factory cost price related to this product SKU", required = true)
    @NotNull(message = "Please specify the product cost price")
    private BigDecimal costPrice;
    @Schema(description = "The gross margin related to this product SKU", required = true)
    @NotNull(message = "Please specify the corresponding product gross margin")
    private BigDecimal grossMargin;
    @Schema(description = "Any associated remarks for the product SKU pricing strategy")
    private String remark;
    @Schema(description = "The current product SKU pricing strategy status")
    @NotNull(message = "Please specify the product price status for batch update")
    private ProductPriceStatus status;
}
