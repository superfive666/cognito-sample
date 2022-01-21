package com.osakakuma.opms.product.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductUrl {
    private String sku;
    private ProductPlatform platform;
    private String url;
}
