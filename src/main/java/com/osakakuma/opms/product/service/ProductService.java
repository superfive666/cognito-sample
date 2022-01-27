package com.osakakuma.opms.product.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.osakakuma.opms.common.entity.FileUpload;
import com.osakakuma.opms.common.entity.LogModule;
import com.osakakuma.opms.common.service.FileUploadService;
import com.osakakuma.opms.common.util.LogBox;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductAuxiliaryMapper productAuxiliaryMapper;
    private final FileUploadService fileUploadService;
    private final JdbcTemplate jdbcTemplate;
    private final PlatformTransactionManager platformTransactionManager;

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
        record.setUrls(productMapper.getProductUrls(sku));

        return record;
    }

    @Transactional(readOnly = true, timeout = 60)
    public PageInfo<ProductRecord> listProductMasterRecords(CognitoUser user, ProductListRequest request) {
        PageHelper.startPage(request.page(), request.pageSize());
        var page  = PageInfo.of(productMapper.listProductRecords(request));
        page.setList(page.getList().stream().map(r -> this.maskSensitiveData(user, r)).toList());

        return page;
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

        // generate create product master record audit log
        var box = new LogBox(jdbcTemplate, platformTransactionManager, user, LogModule.PRODUCT);

        updateImages(request.images(), request.sku());
        updateUrls(request.urls(), request.sku(), box);

        if (admin(user)) {
            var price = getProductPrice(request);
            productMapper.insertProductPrice(price);
        }

        box.log("product.create", Collections.singletonList(request.sku()), null,
                Collections.singleton(request.sku()));
        box.commit();

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

        // create logging box for module update
        var box = new LogBox(jdbcTemplate, platformTransactionManager, user, LogModule.PRODUCT);

        updateProductMaster(user, master, request, box);

        updateImages(request.images(), request.sku());
        updateUrls(request.urls(), request.sku(), box);

        var info = productMapper.getProductInfoBySku(request.sku());
        updateProductInfo(info, request);

        if (admin(user)) {
            var price = productMapper.getProductPriceBySku(request.sku());
            updateProductPrice(price, request, box);
        }

        // commit logs if no exception noted during update
        box.commit();
        return request.sku();
    }

    private void updateProductMaster(CognitoUser user, ProductMaster master, ProductUpdateRequest request, LogBox box) {
        OpmsAssert.isTrue(ProductMasterStatus.DRAFT.equals(request.status()) ||
                        ProductMasterStatus.PENDING.equals(request.status()),
                () -> "Invalid product status during update");

        // before updating product master, log the changes if any
        compareProductMaster(master, request, box);

        BeanUtils.copyProperties(request, master);
        master.setUpdated(Instant.now());
        master.setUpdatedBy(user.username());
        productMapper.updateProductMaster(master);
    }

    private void compareProductMaster(ProductMaster master, ProductUpdateRequest request, LogBox box) {
        var sku = request.sku();
        box.logChange("category", sku, master.getCategory(), request.category());
        box.logChange("sub-category", sku, master.getSubCategory(), request.subCategory());
        box.logChange("brand", sku, master.getBrand(), request.brand());
        box.logChange("four-quadrant", sku, master.getFourQuadrant(), request.fourQuadrant());
        box.logChange("hs-code", sku, master.getHsCode(), request.hsCode());
        box.logChange("specs", sku, master.getSpecs(), request.specs());
        box.logChange("weight", sku, master.getWeight(), request.weight());
        box.logChange("status", sku, master.getStatus(), request.status());
    }

    private void updateProductInfo(ProductInfo info, ProductUpdateRequest request) {
        BeanUtils.copyProperties(request, info);
        productMapper.updateProductInfo(info);
    }

    private void updateProductPrice(ProductPrice price, ProductUpdateRequest request, LogBox box) {
        // compare price before making changes to existing object
        compareProductPrice(price, request, box);

        BeanUtils.copyProperties(request, price);
        price.setSellPrice(Optional.ofNullable(request.sellPrice()).orElse(BigDecimal.ZERO));
        price.setCostPrice(Optional.ofNullable(request.costPrice()).orElse(BigDecimal.ZERO));
        price.setGrossMargin(Optional.ofNullable(request.grossMargin()).orElse(BigDecimal.ZERO));
        price.setStatus(request.priceStatus());
    }

    private void compareProductPrice(ProductPrice price, ProductUpdateRequest request, LogBox box) {
        var sku = request.sku();
        box.logChange("sell-price", sku, price.getSellPrice(), request.sellPrice());
        box.logChange("cost-price", sku, price.getCostPrice(), request.costPrice());
        box.logChange("gross-margin", sku, price.getGrossMargin(), request.grossMargin());
        box.logChange("remark", sku, price.getRemark(), request.remark());
        box.logChange("price-status", sku, price.getStatus(), request.priceStatus());
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

        // Logging of approving the product. Product that is already approved will be rejected by this function
        var box = new LogBox(jdbcTemplate, platformTransactionManager, user, LogModule.PRODUCT);
        box.log("product.approve", Collections.singletonList(sku),
                Collections.singletonList("#product.status.pending@general"),
                Collections.singletonList("#product.status.approve@general"));
        box.commit();

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

        // log product soft deletion in DB
        var box = new LogBox(jdbcTemplate, platformTransactionManager, user, LogModule.PRODUCT);
        box.log("product.delete", Collections.singletonList(sku), Collections.singletonList(sku), null);
        box.commit();

        return sku;
    }

    private boolean admin(CognitoUser user) {
        return user.groups().contains(CognitoRole.ADMIN) || user.groups().contains(CognitoRole.SUPER_ADMIN);
    }

    private void updateUrls(List<Url> urls, String sku, LogBox box) {
        // if no url provided, do nothing
        if (Objects.isNull(urls)) return;

        // compare the URLs before making changes to the database
        compareProductUrls(productMapper.getProductUrls(sku), urls, sku, box);

        // delete existing url to prepare for updates
        productMapper.deleteProductUrl(sku);

        // insert the latest url values regardless if there are updates or not
        urls.stream().map(u -> mapUrl(u, sku)).forEach(productMapper::insertProductUrl);
    }

    private void updateImages(List<Image> images, String sku) {
        // if no parameter sent, do nothing
        if (Objects.isNull(images)) return;

        productMapper.deleteProductImage(sku);

        var i = 0;
        for (Image image : images) {
            var fileId = image.fileId();
            var file = fileUploadService.getFileUploadByFileId(fileId);
            OpmsAssert.isTrue(Objects.nonNull(file),
                    () -> MessageFormat.format("""
                            File id {0} not found in system, try upload the file to system again
                            """,
                            fileId));

            productMapper.insertProductImage(mapFile(file, sku, i++, image));
        }
    }

    private ProductUrl mapUrl(Url url, String sku) {
        return ProductUrl.builder()
                .sku(sku)
                .platform(url.platform())
                .url(url.url())
                .build();
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

    private void compareProductUrls(List<ProductUrl> urls, List<Url> changes, String sku, LogBox box) {
        var field = "url";

        // sort first before comparing the items one by one
        urls.sort(Comparator.comparing(ProductUrl::getPlatform));
        changes.sort(Comparator.comparing(Url::platform));

        int i = 0, j = 0;
        while (i < urls.size() && j < changes.size()) {
            var left = urls.get(i);
            var right = changes.get(j);
            var m = comparePlatform(left.getPlatform(), right.platform());

            if (m > 0) {
                box.logChange(field, sku, formatUrlLogString(left.getPlatform(), left.getUrl()), null);
                j++;
            } else if (m < 0) {
                box.logChange(field, sku, null, formatUrlLogString(right.platform(), right.url()));
                i++;
            } else {
                box.logChange(field, sku,
                        formatUrlLogString(left.getPlatform(), left.getUrl()),
                        formatUrlLogString(right.platform(), right.url()));
                i++; j++;
            }
        }

        while(i < urls.size()) {
            var url = urls.get(i++);
            box.logChange(field, sku, formatUrlLogString(url.getPlatform(), url.getUrl()), null);
        }

        while(j < changes.size()) {
            var url = changes.get(j++);
            box.logChange(field, sku, null, formatUrlLogString(url.platform(), url.url()));
        }
    }

    private int comparePlatform(ProductPlatform left, ProductPlatform right) {
        return left.compareTo(right);
    }

    private String formatUrlLogString(ProductPlatform platform, String url) {
        return String.join(":", platform.name(), url);
    }
}
