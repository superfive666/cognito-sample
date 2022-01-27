package com.osakakuma.opms.price.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.osakakuma.opms.common.entity.LogModule;
import com.osakakuma.opms.common.util.LogBox;
import com.osakakuma.opms.common.util.OpmsAssert;
import com.osakakuma.opms.config.model.CognitoRole;
import com.osakakuma.opms.config.model.CognitoUser;
import com.osakakuma.opms.price.dao.PriceMapper;
import com.osakakuma.opms.price.model.BatchPriceUpdateRequest;
import com.osakakuma.opms.price.model.PriceListRequest;
import com.osakakuma.opms.product.dao.ProductMapper;
import com.osakakuma.opms.product.entity.ProductPrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceService {
    private final ProductMapper productMapper;
    private final PriceMapper priceMapper;
    private final JdbcTemplate jdbcTemplate;
    private final PlatformTransactionManager platformTransactionManager;

    public PageInfo<ProductPrice> listPrices(CognitoUser user, PriceListRequest request) {
        OpmsAssert.isAdmin(user, "[view price info]");
        PageHelper.startPage(request.page(), request.pageSize());

        return PageInfo.of(priceMapper.listPrice(request));
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchUpdatePrice(CognitoUser user, BatchPriceUpdateRequest request) {
        OpmsAssert.authorize(admin(user), () -> "Only admin can update product price information");
        var prices = request.prices();

        // prepare logging function
        var box = new LogBox(jdbcTemplate, platformTransactionManager, user, LogModule.PRICE);
        logBatchPriceChange(box, prices);

        var sql = """
                update product_price
                set sell_price = ?,
                    cost_price = ?,
                    gross_margin = ?,
                    remark = ?,
                    status = ?
                where sku = ?
                """;

        jdbcTemplate.batchUpdate(sql, prices, prices.size(), (ps, price) -> {
            ps.setDouble(1, price.getSellPrice().doubleValue());
            ps.setDouble(2, price.getCostPrice().doubleValue());
            ps.setDouble(3, price.getGrossMargin().doubleValue());
            ps.setString(4, price.getRemark());
            ps.setString(5, price.getStatus().name());
        });

        // commit the logs when price information updated successfully via batch update
        box.commit();
    }

    private void logBatchPriceChange(LogBox box, List<ProductPrice> prices) {
        for (ProductPrice after : prices) {
            var before = productMapper.getProductPriceBySku(after.getSku());
            if (Objects.isNull(before)) {
                log.warn("Product with SKU {} not found during price batch update", after.getSku());
                continue;
            }
            compareProductPrice(before, after, box);
        }
    }

    private boolean admin(CognitoUser user) {
        return user.groups().contains(CognitoRole.ADMIN) || user.groups().contains(CognitoRole.SUPER_ADMIN);
    }

    private void compareProductPrice(ProductPrice before, ProductPrice after, LogBox box) {
        var sku = before.getSku();
        box.logChangePriceBatch("sell-price", sku, before.getSellPrice(), after.getSellPrice());
        box.logChangePriceBatch("cost-price", sku, before.getCostPrice(), after.getCostPrice());
        box.logChangePriceBatch("gross-margin", sku, before.getGrossMargin(), after.getGrossMargin());
        box.logChangePriceBatch("remark", sku, before.getRemark(), after.getRemark());
        box.logChangePriceBatch("price-status", sku, before.getStatus(), after.getStatus());
    }
}
