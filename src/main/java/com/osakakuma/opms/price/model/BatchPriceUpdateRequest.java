package com.osakakuma.opms.price.model;

import com.osakakuma.opms.product.entity.ProductPrice;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Size;
import java.util.List;

public record BatchPriceUpdateRequest(
        @Schema(description = "List of product price information to be updated in batch", required = true)
        @Size(min = 1, message = "The product price list is empty")
        @Size(max = 15, message = "The maximum allowed number of records to be updated in one batch is 15")
        List<ProductPrice> prices
) { }