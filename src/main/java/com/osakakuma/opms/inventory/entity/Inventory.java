package com.osakakuma.opms.inventory.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class Inventory {
    private String sku;
    private String batchCode;
    private Instant expiry;
}
