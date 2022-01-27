/*
 This file stores the start-up script for any configuration content for message_i18n table only
 TEMPLATE:
 insert into message_i18n (locale, key, val, category) values ('', '', '', '') on conflict do nothing;
 */

-- LogAction
insert into message_i18n (locale, key, val, category) values ('en', 'CREATE', 'Create', 'logAction') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'UPDATE', 'Update', 'logAction') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'DELETE', 'Delete', 'logAction') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'CREATE', '添加', 'logAction') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'UPDATE', '更改', 'logAction') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'DELETE', '删除', 'logAction') on conflict do nothing;

-- LogModule
insert into message_i18n (locale, key, val, category) values ('en', 'COMMON', 'Common', 'logModule') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'PRODUCT', 'Product', 'logModule') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'PRICE', 'Price', 'logModule') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'INVENTORY', 'Inventory', 'logModule') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'APP', 'App', 'logModule') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'AUDIT', 'Audit', 'logModule') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'COMMON', '通用', 'logModule') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'PRODUCT', '产品管理', 'logModule') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'PRICE', '价格管理', 'logModule') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'INVENTORY', '仓库管理', 'logModule') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'APP', '小程序', 'logModule') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'AUDIT', '审计记录', 'logModule') on conflict do nothing;

-- product.create
insert into message_i18n (locale, key, val, category) values ('en', 'product.create', 'Create new product', 'logTitle') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.create', 'Create new product ({0}) master record', 'logDescription') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.create', 'Product with SKU-{0} created', 'logContent') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.create', '添加新产品记录', 'logTitle') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.create', '添加新产品({0})总记录', 'logDescription') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.create', '新添加产品SKU号{0}', 'logContent') on conflict do nothing;

-- product.delete
insert into message_i18n (locale, key, val, category) values ('en', 'product.delete', 'Delete existing product', 'logTitle') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.delete', 'Delete product ({0}) master record', 'logDescription') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.delete', 'Product with SKU-{0} deleted', 'logContent') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.delete', '删除产品记录', 'logTitle') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.delete', '删除产品({0})总记录', 'logDescription') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.delete', '删除产品SKU号{0}', 'logContent') on conflict do nothing;

-- product.approve
insert into message_i18n (locale, key, val, category) values ('en', 'product.approve', 'Approve product master', 'logTitle') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.approve', 'Approve product ({0}) master record', 'logDescription') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.approve', 'Product ({0})', 'logContent') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.approve', '审核产品通过', 'logTitle') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.approve', '审核产品({0})通过', 'logDescription') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.approve', '产品({0})', 'logContent') on conflict do nothing;

-- product.update
insert into message_i18n (locale, key, val, category) values ('en', 'product.update', 'Update product master', 'logTitle') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.update', 'Update product ({0}) master record', 'logDescription') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.update', 'Product field name ({0}), product field value ({1})', 'logContent') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.update', '修改产品', 'logTitle') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.update', '修改产品({0})', 'logDescription') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.update', '产品项({0})，产品信息({1})', 'logContent') on conflict do nothing;

-- price.batch
insert into message_i18n (locale, key, val, category) values ('en', 'price.batch', 'Price batch update', 'logTitle') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'price.batch', 'Update product ({0}) via batch', 'logDescription') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'price.batch', 'Pricing of ({0}), value ({1})', 'logContent') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'price.batch', '批量更新产品价格信息', 'logTitle') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'price.batch', '产品({0})价格批量修改', 'logDescription') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'price.batch', '价格项({0}), 价格值({0})', 'logContent') on conflict do nothing;

-- general
insert into message_i18n (locale, key, val, category) values ('en', 'product.status.approve', 'Approve', 'general') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.status.approve', '审核通过', 'general') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.status.pending', 'Pending', 'general') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.status.pending', '待审核', 'general') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.status.draft', 'Draft', 'general') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.status.draft', '草稿', 'general') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.status.in-use', 'In use', 'general') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.status.in-use', '使用中', 'general') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.status.outdated', 'Outdated', 'general') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.status.outdated', '过时', 'general') on conflict do nothing;

-- product_master related fields
insert into message_i18n (locale, key, val, category) values ('en', 'product.field.category', 'Category', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.field.category', '类别', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.field.sub-category', 'Sub category', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.field.sub-category', '附属类别', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.field.brand', 'Brand', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.field.brand', '品牌', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.field.four-quadrant', 'Four quadrant', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.field.four-quadrant', '四项', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.field.hs-code', 'HS Code', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.field.hs-code', 'HS代码', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.field.specs', 'Specifications', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.field.specs', '产品规格', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.field.weight', 'Weight', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.field.weight', '重量', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.field.status', 'Status', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.field.status', '状态', 'field') on conflict do nothing;

-- product_price related fields
insert into message_i18n (locale, key, val, category) values ('en', 'product.field.sell-price', 'Sell price', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.field.sell-price', '卖价', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.field.cost-price', 'Cost price', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.field.cost-price', '进价', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.field.gross-margin', 'Gross margin', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.field.gross-margin', '利润率', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.field.remark', 'Price remark', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.field.remark', '产品定价备注', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.field.price-status', 'Price status', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.field.price-status', '定价状态', 'field') on conflict do nothing;

-- product_url related fields
insert into message_i18n (locale, key, val, category) values ('en', 'product.field.url', 'Product URL', 'field') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.field.url', '产品链接', 'field') on conflict do nothing;
