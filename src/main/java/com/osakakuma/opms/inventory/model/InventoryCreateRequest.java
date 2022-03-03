package com.osakakuma.opms.inventory.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record InventoryCreateRequest(
        @Schema(description = "The globally unique batch code", required = true)
        @Size(max = 40, message = "Maximum batch code length is 40")
        @NotEmpty(message = "Batch code for creating inventory batch is required")
        String batchCode,
        @Schema(description = "Search by SKU - exact search", required = true)
        @Size(max = 20, message = "Maximum SKU length allowed is 20")
        @NotEmpty(message = "SKU for creating inventory batch is required")
        String sku,
        @Min(value = 0L, message = "Expiry timestamp should be non-negative")
        Long expiry
) {
}
