package com.osakakuma.opms.inventory.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record InventoryRecord(
        @Schema(description = "The globally unique batch code for the inventory record", required = true)
        String batchCode,
        @Schema(description = "The globally unique SKU code corresponding to the product", required = true)
        String sku,
        @Schema(description = "The corresponding product name in English")
        String nameEn,
        @Schema(description = "The corresponding product name in Japanese")
        String nameJp,
        @Schema(description = "The corresponding product name in Chinese")
        String nameZh,
        @Schema(description = "Timestamp for the inventory expiry of this batch")
        Instant expiry
) {
}
