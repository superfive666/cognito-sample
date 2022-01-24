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

-- product.price.update


-- general
insert into message_i18n (locale, key, val, category) values ('en', 'product.status.approve', 'Approve', 'general') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.status.pending', 'Pending', 'general') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('en', 'product.status.draft', 'Draft', 'general') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.status.approve', '审核通过', 'general') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.status.pending', '待审核', 'general') on conflict do nothing;
insert into message_i18n (locale, key, val, category) values ('zh', 'product.status.draft', '草稿', 'general') on conflict do nothing;