package com.osakakuma.opms.product.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.osakakuma.opms.product.entity.ProductPlatform;

import io.swagger.v3.oas.annotations.media.Schema;

public record Url (
        @Schema(description = "The external platform that the URL is corresponding to", required = true)
        @NotNull(message = "The platform ID is required")
        ProductPlatform platform,
        @Schema(description = "The fully qualified URL for the product link", required = true)
        @NotEmpty(message = "Missing product URL")
        @Size(max = 500, message = "Maximum URL length allowed is 500")
        @Pattern(regexp = "^(http|https)://[^\\s]+$", message = "Invalid product URL format, it should start with http or https")
        String url
) { }
