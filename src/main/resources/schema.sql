drop table if exists FARMER_DISTRICT;
drop table if exists FARMER;
drop table if exists DISTRICT;

create table if not exists DISTRICT(
    ID bigserial primary key not null,
    NAME varchar(128) not null,
    CODE varchar(30) not null,
    ARCHIVE boolean not null default false
);

create table if not exists FARMER(
    ID bigserial primary key not null,
    NAME varchar(128) not null,
    LEGAL_FORM varchar(2),
    INN varchar(12) not null unique,
    KPP varchar(9),
    OGRN varchar(13),
    DISTRICT_ID bigint not null,
    REGISTRATION_DATE date not null,
    ARCHIVE boolean not null default false,
    constraint FK_DISTRICT foreign key (DISTRICT_ID) references DISTRICT(ID)
);

create table if not exists FARMER_DISTRICT(
    FARMER_ID bigint not null,
    DISTRICT_ID bigint not null,
    constraint FK_FARMER foreign key (FARMER_ID) references FARMER(ID),
    constraint FK_DISTRICT foreign key (DISTRICT_ID) references DISTRICT(ID)
);
