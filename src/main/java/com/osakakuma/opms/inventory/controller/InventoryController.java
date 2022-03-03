package com.osakakuma.opms.inventory.controller;

import com.github.pagehelper.PageInfo;
import com.osakakuma.opms.common.model.BaseResponse;
import com.osakakuma.opms.config.model.CognitoUser;
import com.osakakuma.opms.inventory.model.InventoryListRequest;
import com.osakakuma.opms.inventory.model.InventoryRecord;
import com.osakakuma.opms.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jpi/admin/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @Operation(summary = "List inventory", description = "List existing inventory by requested criteria")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<PageInfo<InventoryRecord>>> listInventory(CognitoUser user,
                                                                                 @Valid InventoryListRequest request) {
        return BaseResponse.success(inventoryService.listInventory(user, request));
    }


}
