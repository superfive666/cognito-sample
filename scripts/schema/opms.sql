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
