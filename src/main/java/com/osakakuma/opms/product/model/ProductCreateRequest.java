package com.osakakuma.opms.product.model;

import com.osakakuma.opms.product.entity.ProductMasterStatus;
import com.osakakuma.opms.product.entity.ProductPriceStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public record ProductCreateRequest(
        @Schema(description = "Global unique identifier for product master", required = true)
        @NotEmpty(message = "SKU is required")
        @Size(max = 20, message = "SKU should be no longer than 20 characters")
        String sku,
        @Schema(description = "The main product category", required = true)
        @NotEmpty(message = "Product main category is required")
        @Size(max = 30, message = "Maximum length for category is 30")
        String category,
        @Schema(description = "The sub product category", required = true)
        @NotEmpty(message = "Product sub category is required")
        @Size(max = 30, message = "Maximum length for category is 30")
        String subCategory,
        @Schema(description = "Product branding", required = true)
        @NotEmpty(message = "Product brand is required")
        @Size(max = 50, message = "Maximum length for brand is 50")
        String brand,
        @Schema(description = "Data analysis result field")
        @Size(max = 100, message = "Maximum length for four quadrant is 100")
        String fourQuadrant,
        @Schema(description = "SG Custom defined coding for product", required = true)
        @NotEmpty(message = "HS Code is required for the product")
        @Size(max = 20, message = "Maximum length for HS Code is 20")
        String hsCode,
        @Schema(description = "The corresponding description for the HS Code", required = true)
        @NotEmpty(message = "HS Code description is required")
        @Size(max = 200, message = "Maximum length for HS Code description is 200")
        String hsCodeDesc,
        @Schema(description = "Free text field for the product specification", required = true)
        @NotEmpty(message = "Product specification is required")
        @Size(max = 30, message = "Maximum length for product specification is 30")
        String specs,
        @Schema(description = "The weights of the product up to 3 decimal places", required = true)
        @NotNull(message = "Product weights are required")
        @Min(value = 0, message = "Product weights should not be negative")
        BigDecimal weight,
        @Schema(description = "Product creation status, can only be PENDING or DRAFT during creation", required = true)
        @NotNull(message = "Product master status should be defined")
        ProductMasterStatus status,
        @Schema(description = "Product name in English, max 300 length")
        @Size(max = 300, message = "Maximum name length allowed is 300")
        String nameEn,
        @Schema(description = "Product name in Japanese, max 300 length")
        @Size(max = 300, message = "Maximum name length allowed is 300")
        String nameJp,
        @Schema(description = "Product name in Chinese, max 300 length")
        @Size(max = 300, message = "Maximum name length allowed is 300")
        String nameZh,
        @Schema(description = "The rich text field for function in English")
        @Size(max = 10000, message = "Maximum rich text content length allowed is 10000")
        String functionEn,
        @Schema(description = "The rich text field for function in Japanese")
        @Size(max = 10000, message = "Maximum rich text content length allowed is 10000")
        String functionJp,
        @Schema(description = "The rich text field for function in Chinese")
        @Size(max = 10000, message = "Maximum rich text content length allowed is 10000")
        String functionZh,
        @Schema(description = "The rich text field for instruction in English")
        @Size(max = 10000, message = "Maximum rich text content length allowed is 10000")
        String instructionEn,
        @Schema(description = "The rich text field for instruction in Japanese")
        @Size(max = 10000, message = "Maximum rich text content length allowed is 10000")
        String instructionJp,
        @Schema(description = "The rich text field for instruction in Chinese")
        @Size(max = 10000, message = "Maximum rich text content length allowed is 10000")
        String instructionZh,
        @Schema(description = "The rich text field for caution in English")
        @Size(max = 10000, message = "Maximum rich text content length allowed is 10000")
        String cautionEn,
        @Schema(description = "The rich text field for caution in Japanese")
        @Size(max = 10000, message = "Maximum rich text content length allowed is 10000")
        String cautionJp,
        @Schema(description = "The rich text field for caution in Chinese")
        @Size(max = 10000, message = "Maximum rich text content length allowed is 10000")
        String cautionZh,
        @Schema(description = "The rich text field for ingredient in English")
        @Size(max = 5000, message = "Maximum rich text content length for ingredient allowed is 5000")
        String ingredientEn,
        @Schema(description = "The rich text field for ingredient in Japanese")
        @Size(max = 5000, message = "Maximum rich text content length for ingredient allowed is 5000")
        String ingredientJp,
        @Schema(description = "The rich text field for ingredient in Chinese")
        @Size(max = 5000, message = "Maximum rich text content length for ingredient allowed is 5000")
        String ingredientZh,
        @Schema(description = "The product origin in English")
        @Size(max = 20, message = "Maximum length for product origin is 20")
        String originEn,
        @Schema(description = "The product origin in Japanese")
        @Size(max = 20, message = "Maximum length for product origin is 20")
        String originJp,
        @Schema(description = "The product origin in Chinese")
        @Size(max = 20, message = "Maximum length for product origin is 20")
        String originZh,
        @Schema(description = "The manufacturer address in English")
        @Size(max = 200, message = "Maximum length for manufacturer address is 200")
        String manufacturerAddrEn,
        @Schema(description = "The manufacturer address in Japanese")
        @Size(max = 200, message = "Maximum length for manufacturer address is 200")
        String manufacturerAddrJp,
        @Schema(description = "The manufacturer address in Chinese")
        @Size(max = 200, message = "Maximum length for manufacturer address is 200")
        String manufacturerAddrZh,
        @Schema(description = "The selling price of the product", defaultValue = "0")
        @Min(value = 0, message = "Product sell price should not be negative")
        BigDecimal sellPrice,
        @Schema(description = "The cost price of the product", defaultValue = "0")
        @Min(value = 0, message = "Product cost price should not be negative")
        BigDecimal costPrice,
        @Schema(description = "The gross margin of the product", defaultValue = "0")
        @Min(value = 0, message = "Product gross margin should not be negative")
        BigDecimal grossMargin,
        @Schema(description = "Any remarks to be saved with the pricing information")
        @Size(max = 150, message = "Maximum remark length allowed is 150 characters")
        String remark,
        @Schema(description = "The price status, should be IN_USE during creation")
        @NotNull(message = "Price status should be set to IN_USE during creation")
        ProductPriceStatus priceStatus,
        @Schema(description = "List of Images from the file upload API, remember the sequence is taken into consideration")
        List<Image> images,
        @Schema(description = "List of external product URLs of the product")
        List<Url> urls
) { }
