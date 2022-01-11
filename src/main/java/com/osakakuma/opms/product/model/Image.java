package com.osakakuma.opms.product.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record Image(
        @Schema(description = "The globally unique file ID for the image uploaded", required = true)
        @NotEmpty(message = "File ID is required")
        String fileId,
        @Schema(description = "Front-end required parameter for storing height", required = true)
        @NotNull(message = "Image height is required to store in DB")
        BigDecimal height,
        @Schema(description = "Front-end required parameter for storing width", required = true)
        @NotNull(message = "Image width is required to store in DB")
        BigDecimal width
) {
}
