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
