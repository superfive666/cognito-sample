package com.osakakuma.opms.common.model;

import io.swagger.v3.oas.annotations.media.Schema;

public enum FileModule {
    @Schema(description = "The product master module")
    PRODUCT,
    @Schema(description = "Inventory management module")
    INVENTORY,
}
