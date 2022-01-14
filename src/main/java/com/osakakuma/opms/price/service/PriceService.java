package com.osakakuma.opms.price.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.osakakuma.opms.common.util.OpmsAssert;
import com.osakakuma.opms.config.model.CognitoUser;
import com.osakakuma.opms.price.dao.PriceMapper;
import com.osakakuma.opms.price.model.BatchPriceUpdateRequest;
import com.osakakuma.opms.price.model.PriceListRequest;
import com.osakakuma.opms.product.entity.ProductPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PriceService {
    private final PriceMapper priceMapper;
    private final JdbcTemplate jdbcTemplate;

    public PageInfo<ProductPrice> listPrices(CognitoUser user, PriceListRequest request) {
        OpmsAssert.isAdmin(user, "[view price info]");
        PageHelper.startPage(request.page(), request.pageSize());

        return PageInfo.of(priceMapper.listPrice(request));
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchUpdatePrice(CognitoUser user, BatchPriceUpdateRequest request) {
        var prices = request.prices();
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
    }

}
