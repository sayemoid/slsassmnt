-- Oct 11, 2024
create table customer_account
(
    balance        decimal(19, 4),
    birth_date     datetime(6)                 not null,
    created_at     datetime(6)                 not null,
    id             bigint                      not null auto_increment,
    last_tnx_date  datetime(6),
    updated_at     datetime(6),
    version        bigint                      not null default 1,
    account_number varchar(255)                not null,
    full_name      varchar(255)                not null,
    uuid_str       varchar(255)                not null,
    account_status enum ('ACTIVE','INACTIVE')  not null,
    account_type   enum ('PREMIUM','STANDARD') not null,
    primary key (id)
) engine = InnoDB;
alter table customer_account
    add constraint UKg12c0xuwhwixshw9x6dbuscss unique (account_number);
alter table customer_account
    add constraint UK607jjhegwcx3xmjq92crrokcm unique (uuid_str);
