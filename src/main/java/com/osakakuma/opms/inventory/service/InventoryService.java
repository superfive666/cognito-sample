package com.osakakuma.opms.inventory.service;

import com.osakakuma.opms.inventory.dao.InventoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryMapper inventoryMapper;
}
