/*
 This file stores the start-up script for any configuration content for message_i18n table only
 TEMPLATE:
 insert into message_i18n (locale, key, val, category) values ('', '', '', '');
 */

-- LogAction
insert into message_i18n (locale, key, val, category) values ('en', 'CREATE', 'Create', 'logAction');
insert into message_i18n (locale, key, val, category) values ('en', 'UPDATE', 'Update', 'logAction');
insert into message_i18n (locale, key, val, category) values ('en', 'DELETE', 'Delete', 'logAction');
insert into message_i18n (locale, key, val, category) values ('zh', 'CREATE', '添加', 'logAction');
insert into message_i18n (locale, key, val, category) values ('zh', 'UPDATE', '更改', 'logAction');
insert into message_i18n (locale, key, val, category) values ('zh', 'DELETE', '删除', 'logAction');

-- LogModule
insert into message_i18n (locale, key, val, category) values ('en', 'COMMON', 'Common', 'logModule');
insert into message_i18n (locale, key, val, category) values ('en', 'PRODUCT', 'Product', 'logModule');
insert into message_i18n (locale, key, val, category) values ('en', 'PRICE', 'Price', 'logModule');
insert into message_i18n (locale, key, val, category) values ('en', 'INVENTORY', 'Inventory', 'logModule');
insert into message_i18n (locale, key, val, category) values ('en', 'APP', 'App', 'logModule');
insert into message_i18n (locale, key, val, category) values ('en', 'AUDIT', 'Audit', 'logModule');
insert into message_i18n (locale, key, val, category) values ('zh', 'COMMON', '通用', 'logModule');
insert into message_i18n (locale, key, val, category) values ('zh', 'PRODUCT', '产品管理', 'logModule');
insert into message_i18n (locale, key, val, category) values ('zh', 'PRICE', '价格管理', 'logModule');
insert into message_i18n (locale, key, val, category) values ('zh', 'INVENTORY', '仓库管理', 'logModule');
insert into message_i18n (locale, key, val, category) values ('zh', 'APP', '小程序', 'logModule');
insert into message_i18n (locale, key, val, category) values ('zh', 'AUDIT', '审计记录', 'logModule');

-- product.create
insert into message_i18n (locale, key, val, category) values ('en', 'product.create', 'Create new product', 'logTitle');
insert into message_i18n (locale, key, val, category) values ('en', 'product.create', 'Create new product ({0}) master record', 'logDescription');
insert into message_i18n (locale, key, val, category) values ('en', 'product.create', 'Product with SKU-{0} created', 'logContent');
insert into message_i18n (locale, key, val, category) values ('zh', 'product.create', '添加新产品记录', 'logTitle');
insert into message_i18n (locale, key, val, category) values ('zh', 'product.create', '添加新产品({0})总记录', 'logDescription');
insert into message_i18n (locale, key, val, category) values ('zh', 'product.create', '新添加产品SKU号{0}', 'logContent');

-- product.delete
insert into message_i18n (locale, key, val, category) values ('en', 'product.delete', 'Delete existing product', 'logTitle');
insert into message_i18n (locale, key, val, category) values ('en', 'product.delete', 'Delete product ({0}) master record', 'logDescription');
insert into message_i18n (locale, key, val, category) values ('en', 'product.delete', 'Product with SKU-{0} deleted', 'logContent');
insert into message_i18n (locale, key, val, category) values ('zh', 'product.delete', '删除产品记录', 'logTitle');
insert into message_i18n (locale, key, val, category) values ('zh', 'product.delete', '删除产品({0})总记录', 'logDescription');
insert into message_i18n (locale, key, val, category) values ('zh', 'product.delete', '删除产品SKU号{0}', 'logContent');

-- product.approve
insert into message_i18n (locale, key, val, category) values ('en', 'product.approve', 'Approve product master', 'logTitle');
insert into message_i18n (locale, key, val, category) values ('en', 'product.approve', 'Approve product ({0}) master record', 'logDescription');
insert into message_i18n (locale, key, val, category) values ('en', 'product.approve', 'Product ({0})', 'logContent');
insert into message_i18n (locale, key, val, category) values ('zh', 'product.approve', '审核产品通过', 'logTitle');
insert into message_i18n (locale, key, val, category) values ('zh', 'product.approve', '审核产品({0})通过', 'logDescription');
insert into message_i18n (locale, key, val, category) values ('zh', 'product.approve', '产品({0})', 'logContent');

-- product.price.update


-- general
insert into message_i18n (locale, key, val, category) values ('en', 'product.status.approve', 'Approve', 'general');
insert into message_i18n (locale, key, val, category) values ('en', 'product.status.pending', 'Pending', 'general');
insert into message_i18n (locale, key, val, category) values ('en', 'product.status.draft', 'Draft', 'general');
insert into message_i18n (locale, key, val, category) values ('zh', 'product.status.approve', '审核通过', 'general');
insert into message_i18n (locale, key, val, category) values ('zh', 'product.status.pending', '待审核', 'general');
insert into message_i18n (locale, key, val, category) values ('zh', 'product.status.draft', '草稿', 'general');