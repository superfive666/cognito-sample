package com.osakakuma.opms.price.controller;

import com.github.pagehelper.PageInfo;
import com.osakakuma.opms.common.model.BaseResponse;
import com.osakakuma.opms.config.model.CognitoUser;
import com.osakakuma.opms.price.model.BatchPriceUpdateRequest;
import com.osakakuma.opms.price.model.PriceListRequest;
import com.osakakuma.opms.price.service.PriceService;
import com.osakakuma.opms.product.entity.ProductPrice;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jpi/admin/price")
public class PriceController {
    private final PriceService priceService;

    @Operation(summary = "List product price", description = "List product pricing information only")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<PageInfo<ProductPrice>>> listPrice(CognitoUser user, @Valid PriceListRequest request) {
        return BaseResponse.success(priceService.listPrices(user, request));
    }

    @Operation(summary = "Batch price update", description = "Update a batch of pricing information")
    @PutMapping(value = "/batch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<Boolean>> batchUpdatePrice(CognitoUser user, @Valid BatchPriceUpdateRequest request) {
        priceService.batchUpdatePrice(user, request);

        return BaseResponse.success(Boolean.TRUE);
    }
}
