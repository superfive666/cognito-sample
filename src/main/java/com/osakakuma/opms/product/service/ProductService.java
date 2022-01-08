package com.osakakuma.opms.product.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.osakakuma.opms.common.util.OpmsAssert;
import com.osakakuma.opms.config.model.CognitoRole;
import com.osakakuma.opms.config.model.CognitoUser;
import com.osakakuma.opms.product.dao.ProductMapper;
import com.osakakuma.opms.product.entity.*;
import com.osakakuma.opms.product.model.ProductCreateRequest;
import com.osakakuma.opms.product.model.ProductListRequest;
import com.osakakuma.opms.product.model.ProductRecord;
import com.osakakuma.opms.product.model.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

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
        var master = getProductMaster(user, request);
        productMapper.insertProductMaster(master);

        var info = getProductInfo(request);
        productMapper.insertProductInfo(info);

        var price = getProductPrice(request);
        productMapper.insertProductPrice(price);

        return request.sku();
    }

    private ProductMaster getProductMaster(CognitoUser user, ProductCreateRequest request) {
        OpmsAssert.isTrue(ProductMasterStatus.DRAFT.equals(request.status()) ||
                ProductMasterStatus.PENDING.equals(request.status()),
                () -> "Invalid product status during creation");
        return ProductMaster.builder()
                .sku(request.sku())
                .category(request.category())
                .subCategory(request.subCategory())
                .brand(request.brand())
                .fourQuadrant(request.fourQuadrant())
                .hsCode(request.hsCode())
                .hsCodeDesc(request.hsCodeDesc())
                .specs(request.specs())
                .weight(request.weight())
                .status(request.status())
                .created(Instant.now())
                .createdBy(user.username())
                .build();
    }

    private ProductInfo getProductInfo(ProductCreateRequest request) {
        return ProductInfo.builder()
                .sku(request.sku())
                .nameEn(request.nameEn())
                .nameJp(request.nameJp())
                .nameZh(request.nameZh())
                .functionEn(request.functionEn())
                .functionJp(request.functionJp())
                .functionZh(request.functionZh())
                .instructionEn(request.instructionEn())
                .instructionJp(request.instructionJp())
                .instructionZh(request.instructionZh())
                .cautionEn(request.cautionEn())
                .cautionJp(request.cautionJp())
                .cautionZh(request.cautionZh())
                .ingredientEn(request.ingredientEn())
                .ingredientJp(request.ingredientJp())
                .ingredientZh(request.ingredientZh())
                .originEn(request.originEn())
                .originJp(request.originJp())
                .manufacturerAddrEn(request.manufacturerAddrEn())
                .manufacturerAddrJp(request.manufacturerAddrJp())
                .manufacturerAddrZh(request.manufacturerAddrZh())
                .build();
    }

    private ProductPrice getProductPrice(ProductCreateRequest request) {
        OpmsAssert.isTrue(ProductPriceStatus.IN_USE.equals(request.priceStatus()),
                () -> "The status of pricing information should be set to IN_USE during creation");

        return ProductPrice.builder()
                .sellPrice(Optional.ofNullable(request.sellPrice()).orElse(BigDecimal.ZERO))
                .costPrice(Optional.ofNullable(request.costPrice()).orElse(BigDecimal.ZERO))
                .grossMargin(Optional.ofNullable(request.grossMargin()).orElse(BigDecimal.ZERO))
                .remark(request.remark())
                .status(request.priceStatus())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public String updateProductMasterRecord(CognitoUser user, ProductUpdateRequest request) {
        var master = productMapper.getProductMasterBySku(request.sku());
        updateProductMaster(user, master, request);

        var info = productMapper.getProductInfoBySku(request.sku());
        updateProductInfo(info, request);

        var price = productMapper.getProductPriceBySku(request.sku());
        updateProductPrice(price, request);

        return request.sku();
    }

    private void updateProductMaster(CognitoUser user, ProductMaster master, ProductUpdateRequest request) {
        OpmsAssert.isTrue(ProductMasterStatus.DRAFT.equals(request.status()) ||
                        ProductMasterStatus.PENDING.equals(request.status()),
                () -> "Invalid product status during update");


    }

    private void updateProductInfo(ProductInfo info, ProductUpdateRequest request) {

    }

    private void updateProductPrice(ProductPrice price, ProductUpdateRequest request) {

    }

    @Transactional(rollbackFor = Exception.class)
    public String approveProductMasterRecord(CognitoUser user, String sku) {
        var master = productMapper.getProductMasterBySku(sku);
        OpmsAssert.isTrue(user.groups().contains(CognitoRole.ADMIN) ||
                user.groups().contains(CognitoRole.SUPER_ADMIN), () -> "Only admin user can approve product master");

        master.setApproved(Instant.now());
        master.setApprovedBy(user.username());
        master.setStatus(ProductMasterStatus.APPROVED);

        return sku;
    }

    @Transactional(rollbackFor = Exception.class)
    public String deleteProductMasterRecord(CognitoUser user, String sku) {
        var master = productMapper.getProductMasterBySku(sku);
        master.setStatus(ProductMasterStatus.REMOVED);
        master.setUpdated(Instant.now());
        master.setUpdatedBy(user.username());

        // update the status as REMOVED and set the current updated timestamp
        productMapper.updateProductMaster(master);

        return sku;
    }
}
