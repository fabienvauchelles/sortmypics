drop table MFILE if exists cascade constraints;
drop table MGROUP if exists cascade constraints;
drop table MPLACE if exists cascade constraints;
drop table MPLACE_CACHE if exists cascade constraints;
drop table MLATITUDE_CACHE if exists cascade constraints;

create table MFILE
(
    F_MD5HASH varchar(32) not null,
    F_NAME varchar(512) not null,
    F_EXTENSION varchar(64) not null,
    F_PATH varchar(2048) not null,
    F_SIZE bigint not null,
    F_TYPE smallint not null,
    F_CREATED timestamp not null,
    F_ADDED timestamp not null,

    F_LAT double null,
    F_LNG double null,
    F_CAMERA varchar(512) null,

    F_P_ID varchar(36) null,

    F_G_ID varchar(36) null,

    primary key (F_MD5HASH)
);

create table MGROUP
(
    G_ID varchar(36) not null,
    G_CAL timestamp not null,
    G_P_ID varchar(36) null,

    G_MIN varchar(32) null,
    G_MAX varchar(32) null,

    primary key (G_ID),
    unique (G_CAL, G_P_ID)
);

create table MPLACE
(
    P_ID varchar(36) not null,
    P_LOCATION varchar(512) not null,
    P_LAT double not null,
    P_LNG double not null,

    primary key (P_ID)
);

create table MPLACE_CACHE
(
    PC_ID varchar(36) not null,
    PC_LOCATION varchar(512) not null,
    PC_LAT double not null,
    PC_LNG double not null,

    primary key (PC_ID)
);

create table MLATITUDE_CACHE
(
    LT_ID varchar(36) not null,
    LT_CAL timestamp not null,
    LT_LAT double null,
    LT_LNG double null,
    LT_ACC double null,

    primary key (LT_ID)
);

alter table MFILE add constraint F_G_ID_FK foreign key (F_G_ID) references MGROUP(G_ID);
alter table MFILE add constraint F_P_ID_FK foreign key (F_P_ID) references MPLACE(P_ID);
alter table MGROUP add constraint G_MIN_FK foreign key (G_MIN) references MFILE(F_MD5HASH);
alter table MGROUP add constraint G_MAX_FK foreign key (G_MAX) references MFILE(F_MD5HASH);
alter table MGROUP add constraint G_P_ID_FK foreign key (G_P_ID) references MPLACE(P_ID);
