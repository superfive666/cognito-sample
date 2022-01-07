package com.osakakuma.opms.product.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.osakakuma.opms.config.model.CognitoUser;
import com.osakakuma.opms.product.dao.ProductMapper;
import com.osakakuma.opms.product.model.ProductCreateRequest;
import com.osakakuma.opms.product.model.ProductListRequest;
import com.osakakuma.opms.product.model.ProductRecord;
import com.osakakuma.opms.product.model.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;

    @Transactional(readOnly = true, timeout = 60)
    public ProductRecord getProductRecordDetails(CognitoUser user, String sku) {
        log.debug("User {} is retrieving product sku-{} details", user.username(), sku);

        return maskSensitiveData(user, productMapper.getProductRecordBySku(sku));
    }

    @Transactional(readOnly = true, timeout = 60)
    public PageInfo<ProductRecord> listProductMasterRecords(CognitoUser user, ProductListRequest request) {
        PageHelper.startPage(request.page(), request.pageSize());
        var result = productMapper.listProductRecords(request)
                .stream().map(r -> this.maskSensitiveData(user, r)).toList();

        return PageInfo.of(result);
    }

    private ProductRecord maskSensitiveData(CognitoUser user, ProductRecord record) {
        // TODO: in the future to add data masking logic for sensitive pricing information
        return record;
    }

    @Transactional(rollbackFor = Exception.class)
    public String createProductMasterRecord(CognitoUser user, ProductCreateRequest request) {

        // TODO: return sku of the product record
        return "";
    }

    @Transactional(rollbackFor = Exception.class)
    public String updateProductMasterRecord(CognitoUser user, ProductUpdateRequest request) {

        // TODO: return sku of the product record
        return "";
    }

    @Transactional(rollbackFor = Exception.class)
    public String deleteProductMasterRecord(CognitoUser user, String sku) {

        return sku;
    }
}
