package com.osakakuma.opms.inventory.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.osakakuma.opms.config.model.CognitoUser;
import com.osakakuma.opms.inventory.dao.InventoryMapper;
import com.osakakuma.opms.inventory.model.InventoryListRequest;
import com.osakakuma.opms.inventory.model.InventoryRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryMapper inventoryMapper;

    public PageInfo<InventoryRecord> listInventory(CognitoUser user, InventoryListRequest request) {
        PageHelper.startPage(request.page(), request.pageSize());


        return null;
    }
}
