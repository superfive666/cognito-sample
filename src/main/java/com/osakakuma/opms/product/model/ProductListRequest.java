package com.osakakuma.opms.product.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.api.annotations.ParameterObject;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@ParameterObject
public record ProductListRequest(
        @Schema(description = "page number starting with 1", required = true)
        @NotNull(message = "page number is missing")
        @Min(value = 1, message = "Invalid page number")
        Integer page,
        @Schema(description = "page size", required = true)
        @NotNull(message = "page size is missing")
        @Min(value = 10, message = "Minimum page size is 10")
        Integer pageSize,
        @Schema(description = "Search by SKU - exact search")
        String sku,
        @Schema(description = "Search by category - fuzzy search, case insensitive")
        String category,
        @Schema(description = "Search by brand - fuzzy search, case insensitive")
        String brand,
        @Schema(description = "Search by name (all languages) - fuzzy search, case insensitive")
        String name,
        @Schema(description = "Order column name")
        ProductMasterOrderColumn order,
        @Schema(description = "Ascending or descending", defaultValue = "false")
        Boolean asc
) {
        public ProductListRequest {
                if (Objects.isNull(asc)) asc = Boolean.FALSE;
        }
}
