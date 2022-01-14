set search_path = opms;
-- message template
create table if not exists message_i18n (
    locale character varying (2),
    key character varying (100),
    val character varying (500),
    category character varying (100),
    constraint pk_message_i18n primary key (locale, key, category)
);
comment on table message_i18n is 'Messaging template with multiple translation catered';
comment on column message_i18n.locale is '2-character standard language code, such as en for English';
comment on column message_i18n.key is 'Message key for locating the translated text - required';
comment on column message_i18n.val is 'The translated message content to be stored';
comment on column message_i18n.category is 'Message category for locating the translated text - required';
grant select, insert, update, delete on message_i18n to opms_app_role;

-- audit log table for all OPMS modules
create table if not exists audit_log (
    username character varying (30) not null,
    log_time timestamp default current_timestamp not null,
    log_action character varying (10) default 'CREATE' not null,
    log_module character varying (10) default 'PRODUCT' not null,
    log_title character varying (50) not null,
    log_description character varying (200) not null,
    val_before character varying (500),
    val_after character varying (500)
);
comment on table audit_log is 'Audit log table for storing all OPMS backend module changes performed';
comment on column audit_log.username is 'The AWS Cognito registered username';
comment on column audit_log.log_time is 'The timestamp that the log entry is generated';
comment on column audit_log.log_action is 'The log action enum type value such as CREATE, UPDATE, DELETE';
comment on column audit_log.log_module is 'The logging module of the OPMS system, corresponding to the backend modules, such as PRODUCT, PRICE, INVENTORY, etc.';
comment on column audit_log.log_title is 'The displayed log title translatable via message_i18n table (with parameterized content)';
comment on column audit_log.log_description is 'The displayed long description text for the audit log, translatable via message_i18n table';
comment on column audit_log.val_before is 'The before value, translatable via message_i18n table';
comment on column audit_log.val_after is 'The after value, translatable via message_i18n table';
grant select, insert, update, delete on audit_log to opms_app_role;

-- product module master table
create table if not exists product_master (
    sku character varying(20) primary key,
    category character varying(30) not null,
    sub_category character varying(30) not null,
    brand character varying(50) not null,
    four_quadrant character varying(100),
    hs_code character varying(20) not null,
    hs_code_desc character varying(200) not null,
    specs character varying(30) default 'NA' not null,
    weight decimal(9,3) default 0 not null,
    created timestamp default current_timestamp not null,
    created_by character varying(30) not null,
    updated timestamp,
    updated_by character varying(30),
    status character varying(10) default 'DRAFT' not null,
    approved timestamp,
    approved_by character varying(30)
);
comment on table product_master is 'The master record table for product listing, information in this table all required fields';
comment on column product_master.sku is 'Product globally unique identifier';
comment on column product_master.category is 'Free text field for product main category';
comment on column product_master.sub_category is 'Free text field for product sub category';
comment on column product_master.brand is 'Free text field for product branding';
comment on column product_master.four_quadrant is 'Free text field for data analysis result field - four quadrant categorization';
comment on column product_master.hs_code is 'Singapore custom defined HS Code for the product';
comment on column product_master.hs_code_desc is 'Free text field for describing the HS Code';
comment on column product_master.specs is 'Free text field for describing the product specifications';
comment on column product_master.weight is 'Numeric information relating to product weights with maximum 3 decimal points';
grant select, insert, update, delete on product_master to opms_app_role;

create table if not exists product_info (
    sku character varying(20) primary key,
    name_en character varying(300),
    name_jp character varying(300),
    name_zh character varying(300),
    function_en character varying(10000),
    function_jp character varying(10000),
    function_zh character varying(10000),
    instruction_en character varying(10000),
    instruction_jp character varying(10000),
    instruction_zh character varying(10000),
    caution_en character varying(10000),
    caution_jp character varying(10000),
    caution_zh character varying(10000),
    ingredient_en character varying(5000),
    ingredient_jp character varying(5000),
    ingredient_zh character varying(5000),
    origin_en character varying(20),
    origin_jp character varying(20),
    manufacturer_addr_en character varying(200),
    manufacturer_addr_jp character varying(200),
    manufacturer_addr_zh character varying(200)
);
comment on table product_info is 'Product related rich-text information, all fields in this table are nullable';
comment on column product_info.sku is 'Product globally unique identifier';
grant select, insert, update, delete on product_info to opms_app_role;

create table if not exists product_price (
    sku character varying(20) primary key,
    sell_price money not null,
    cost_price money not null,
    gross_margin decimal(6,3) not null,
    remark character varying (150),
    status character varying (10) default 'IN_USE' not null
);
comment on table product_price is 'Product pricing information';
comment on column product_price.sell_price is 'The original price directly from supplier';
comment on column product_price.cost_price is 'The discounted price for this current platform';
comment on column product_price.gross_margin is 'The margin that the current product is earning';
comment on column product_price.remark is 'Free text field for user input on pricing information';
comment on column product_price.status is 'Enum value of current pricing information - IN_USE is default';
grant select, insert, update, delete on product_price to opms_app_role;

create index if not exists idx_sell_price on product_price (sell_price);
create index if not exists idx_cost_price on product_price (cost_price);
create index if not exists idx_gross_margin on product_price (gross_margin);

-- File upload common table
create table if not exists file_upload (
    id character varying (36) primary key,
    module character varying (10) not null,
    file_name character varying (50) not null,
    file_ext character varying (6) not null,
    author character varying (30) not null,
    created timestamp default current_timestamp not null,
    updated timestamp,
    status character varying (10) default 'OK' not null
);
comment on table file_upload is 'The centrally managed repository for all files uploaded to S3 bucket of OPMS';
comment on column file_upload.id is 'Globally unique ID for identifying the file uploaded within the system';
comment on column file_upload.module is 'The current modules available in system, referring to FileModule Enum class';
comment on column file_upload.file_name is 'The file name uploaded (without file extension)';
comment on column file_upload.file_ext is 'The file extension, for example jpg';
comment on column file_upload.author is 'The cognito username who uploaded this file';
comment on column file_upload.created is 'The file upload creation timestamp';
comment on column file_upload.updated is 'The file upload update timestamp';
comment on column file_upload.status is 'The file status in S3, OK - available in S3, ARCHIVED - archived but still available, DELETED - removed from s3';
grant select, insert, update, delete on file_upload to opms_app_role;

create index if not exists idx_file_author on file_upload(author);

-- Product Master Images Management
create table if not exists product_image (
    sku character varying (20),
    file_id character varying (36),
    height numeric (9,2) default 0 not null,
    width numeric (9,2) default 0 not null,
    path character varying not null,
    seq numeric (2,0) default 0 not null,
    constraint fk_product_image_sku foreign key (sku)
    references product_master (sku)
    on delete cascade,
    constraint fk_product_image_file_id foreign key (file_id)
    references file_upload (id)
    on delete cascade
);
comment on table product_image is 'Product image link corresponding to the product master table information';
comment on column product_image.sku is 'Globally unique identifier for the product master record';
comment on column product_image.file_id is 'Foreign key mapping to the ID column of file_upload table';
comment on column product_image.height is 'The image height parameter for front-end rendering purpose';
comment on column product_image.width is 'The image width parameter for front-end rendering purpose';
comment on column product_image.path is 'The image URL request path parameter without the host name';
comment on column product_image.seq is 'Zero-based index for the images related to the product record for ordering purpose';
grant select, insert, update, delete on product_image to opms_app_role;
