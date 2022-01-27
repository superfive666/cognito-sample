package com.osakakuma.opms.audit.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum LogModule {
    @Schema(description = "The common modules and services")
    COMMON,
    @Schema(description = "Core module for the OPMS system - Product")
    PRODUCT,
    @Schema(description = "Pricing information and update module")
    PRICE,
    @Schema(description = "Inventory management module")
    INVENTORY,
    @Schema(description = "Wechat application integration")
    APP,
    @Schema(description = "Audit log related functions")
    AUDIT
}
