package com.osakakuma.opms.product.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.osakakuma.opms.common.entity.FileUpload;
import com.osakakuma.opms.common.service.FileUploadService;
import com.osakakuma.opms.common.util.OpmsAssert;
import com.osakakuma.opms.config.model.CognitoRole;
import com.osakakuma.opms.config.model.CognitoUser;
import com.osakakuma.opms.product.dao.ProductAuxiliaryMapper;
import com.osakakuma.opms.product.dao.ProductMapper;
import com.osakakuma.opms.product.entity.*;
import com.osakakuma.opms.product.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductAuxiliaryMapper productAuxiliaryMapper;
    private final FileUploadService fileUploadService;

    @Transactional(readOnly = true, timeout = 60)
    public Boolean skuAvailable(String sku) {
        return !Boolean.TRUE.equals(productAuxiliaryMapper.skuExists(sku));
    }

    @Transactional(readOnly = true, timeout = 60)
    public ProductRecord getProductRecordDetails(CognitoUser user, String sku) {
        log.debug("User {} is retrieving product sku-{} details", user.username(), sku);

        var record = maskSensitiveData(user, productMapper.getProductRecordBySku(sku));
        OpmsAssert.isTrue(Objects.nonNull(record), () -> "Record not found with SKU " + sku);

        record.setImages(productMapper.getProductImages(sku));

        return record;
    }

    @Transactional(readOnly = true, timeout = 60)
    public PageInfo<ProductRecord> listProductMasterRecords(CognitoUser user, ProductListRequest request) {
        PageHelper.startPage(request.page(), request.pageSize());
        var result = productMapper.listProductRecords(request)
                .stream().map(r -> this.maskSensitiveData(user, r)).toList();

        return PageInfo.of(result);
    }

    private ProductRecord maskSensitiveData(CognitoUser user, ProductRecord record) {
        if (!admin(user)) {
           record.setSellPrice(null);
           record.setCostPrice(null);
           record.setGrossMargin(null);
           record.setRemark(null);
           record.setPriceStatus(null);
        }

        return record;
    }

    @Transactional(rollbackFor = Exception.class)
    public String createProductMasterRecord(CognitoUser user, ProductCreateRequest request) {
        var master = getProductMaster(user, request);
        productMapper.insertProductMaster(master);

        var info = getProductInfo(request);
        productMapper.insertProductInfo(info);

        updateImages(request.images(), request.sku());

        if (admin(user)) {
            var price = getProductPrice(request);
            productMapper.insertProductPrice(price);
        }

        return request.sku();
    }

    private ProductMaster getProductMaster(CognitoUser user, ProductCreateRequest request) {
        OpmsAssert.isTrue(ProductMasterStatus.DRAFT.equals(request.status()) ||
                ProductMasterStatus.PENDING.equals(request.status()),
                () -> "Invalid product status during creation");
        var master =  ProductMaster.builder()
                .created(Instant.now())
                .createdBy(user.username())
                .build();
        BeanUtils.copyProperties(request, master);

        return master;
    }

    private ProductInfo getProductInfo(ProductCreateRequest request) {
        var info = ProductInfo.builder().build();
        BeanUtils.copyProperties(request, info);

        return info;
    }

    private ProductPrice getProductPrice(ProductCreateRequest request) {
        OpmsAssert.isTrue(ProductPriceStatus.IN_USE.equals(request.priceStatus()),
                () -> "The status of pricing information should be set to IN_USE during creation");

        return ProductPrice.builder()
                .sku(request.sku())
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
        OpmsAssert.isTrue(Objects.nonNull(master), () -> "Product not exists or already deleted");

        updateProductMaster(user, master, request);

        updateImages(request.images(), request.sku());

        var info = productMapper.getProductInfoBySku(request.sku());
        updateProductInfo(info, request);

        if (admin(user)) {
            var price = productMapper.getProductPriceBySku(request.sku());
            updateProductPrice(price, request);
        }

        return request.sku();
    }

    private void updateProductMaster(CognitoUser user, ProductMaster master, ProductUpdateRequest request) {
        OpmsAssert.isTrue(ProductMasterStatus.DRAFT.equals(request.status()) ||
                        ProductMasterStatus.PENDING.equals(request.status()),
                () -> "Invalid product status during update");

        BeanUtils.copyProperties(request, master);
        master.setUpdated(Instant.now());
        master.setUpdatedBy(user.username());
        productMapper.updateProductMaster(master);
    }

    private void updateProductInfo(ProductInfo info, ProductUpdateRequest request) {
        BeanUtils.copyProperties(request, info);
        productMapper.updateProductInfo(info);
    }

    private void updateProductPrice(ProductPrice price, ProductUpdateRequest request) {
        BeanUtils.copyProperties(request, price);
        price.setSellPrice(Optional.ofNullable(request.sellPrice()).orElse(BigDecimal.ZERO));
        price.setCostPrice(Optional.ofNullable(request.costPrice()).orElse(BigDecimal.ZERO));
        price.setGrossMargin(Optional.ofNullable(request.grossMargin()).orElse(BigDecimal.ZERO));
        price.setStatus(request.priceStatus());
    }

    @Transactional(rollbackFor = Exception.class)
    public String approveProductMasterRecord(CognitoUser user, String sku) {
        var master = productMapper.getProductMasterBySku(sku);
        OpmsAssert.isTrue(user.groups().contains(CognitoRole.ADMIN) ||
                user.groups().contains(CognitoRole.SUPER_ADMIN), () -> "Only admin user can approve product master");

        OpmsAssert.isTrue(Objects.nonNull(master), () -> "Product not exists or already deleted");
        OpmsAssert.isTrue(!ProductMasterStatus.APPROVED.equals(master.getStatus()), () -> "Already approved");

        master.setApproved(Instant.now());
        master.setApprovedBy(user.username());
        master.setStatus(ProductMasterStatus.APPROVED);

        return sku;
    }

    @Transactional(rollbackFor = Exception.class)
    public String deleteProductMasterRecord(CognitoUser user, String sku) {
        var master = productMapper.getProductMasterBySku(sku);
        OpmsAssert.isTrue(Objects.nonNull(master), () -> "Product not exists or already deleted");

        master.setStatus(ProductMasterStatus.REMOVED);
        master.setUpdated(Instant.now());
        master.setUpdatedBy(user.username());

        // update the status as REMOVED and set the current updated timestamp
        productMapper.updateProductMaster(master);

        return sku;
    }

    private boolean admin(CognitoUser user) {
        return user.groups().contains(CognitoRole.ADMIN) || user.groups().contains(CognitoRole.SUPER_ADMIN);
    }

    private void updateImages(List<Image> images, String sku) {
        if (CollectionUtils.isEmpty(images)) return;

        productMapper.deleteProductImage(sku);

        var i = 0;
        for (Image image : images) {
            var file = fileUploadService.getFileUploadByFileId(image.fileId());
            productMapper.insertProductImage(mapFile(file, sku, i++, image));
        }
    }

    private ProductImage mapFile(FileUpload fileUpload, String sku, int seq, Image image) {
        return ProductImage.builder()
                .sku(sku)
                .fileId(fileUpload.getId())
                .height(image.height())
                .width(image.width())
                .path(getFilePath(fileUpload))
                .seq(seq)
                .build();
    }

    private String getFilePath(FileUpload fileUpload) {
        // base path is prefixed with "s3"
        return "s3" + "/" +
                // module name to separate the files
                fileUpload.getModule() + "/" +
                // username sub-path
                fileUpload.getAuthor() + "/" +
                // date string to prevent files with same name
                fileUploadService.getDateString(fileUpload.getCreated()) + "/" +
                // filename.file_ext
                fileUpload.getFileName() + "." + fileUpload.getFileExt();
    }
}
