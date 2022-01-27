package com.osakakuma.opms.inventory.controller;

import com.osakakuma.opms.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

}
