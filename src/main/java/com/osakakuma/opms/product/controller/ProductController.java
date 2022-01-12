package com.osakakuma.opms.product.controller;

import com.github.pagehelper.PageInfo;
import com.osakakuma.opms.common.model.BaseResponse;
import com.osakakuma.opms.config.model.CognitoUser;
import com.osakakuma.opms.product.model.ProductCreateRequest;
import com.osakakuma.opms.product.model.ProductListRequest;
import com.osakakuma.opms.product.model.ProductRecord;
import com.osakakuma.opms.product.model.ProductUpdateRequest;
import com.osakakuma.opms.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/jpi/admin/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Check SKU", description = "Check if the sku is available to be used")
    @GetMapping(value = "/skuAvailable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<Boolean>> skuExists(String sku) {
        return BaseResponse.success(productService.skuAvailable(sku));
    }

    @Operation(summary = "Get Product Details", description = "Retrieve product details by SKU")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<ProductRecord>> getProductRecord(CognitoUser user, String sku) {
        return BaseResponse.success(productService.getProductRecordDetails(user, sku));
    }

    @Operation(summary = "List Products", description = "List the products with pagination")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<PageInfo<ProductRecord>>> listProductRecords(CognitoUser user,
                                                                                    @Valid ProductListRequest request) {
        return BaseResponse.success(productService.listProductMasterRecords(user, request));
    }

    @Operation(summary = "Create Product", description = "Create product or draft based on status, return SKU if success")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<String>> createProductRecord(CognitoUser user, @RequestBody @Valid ProductCreateRequest request) {
        return BaseResponse.success(productService.createProductMasterRecord(user, request));
    }

    @Operation(summary = "Update Product", description = "Update product or draft based on status, return SKU if success")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<String>> updateProductRecord(CognitoUser user, @RequestBody @Valid ProductUpdateRequest request) {
        return BaseResponse.success(productService.updateProductMasterRecord(user, request));
    }

    @Operation(summary = "Approve Product", description = "Approve product based on the SKU parameter in request, return SKU if success")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<String>> approveProductRecord(CognitoUser user, String sku) {
        return BaseResponse.success(productService.approveProductMasterRecord(user, sku));
    }

    @Operation(summary = "Delete Product", description = "Delete product based on the SKU parameter in request, return SKU if success")
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<String>> deleteProduct(CognitoUser user, String sku) {
        return BaseResponse.success(productService.deleteProductMasterRecord(user, sku));
    }
}
