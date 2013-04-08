drop table PLACE if exists cascade constraints;
drop table DESCRIPTION if exists cascade constraints;
drop table LISTING if exists cascade constraints;


create table LISTING
(
    L_ID bigint auto_increment not null,
    L_NAME varchar(1024) not null,
    L_DATE timestamp not null,

    primary key (L_ID)
);

create table DESCRIPTION
(
    D_ID varchar(32) not null,
    D_TYPE smallint not null,
    D_CREATED_DATE timestamp null,
    D_GPS_LAT double null,
    D_GPS_LNG double null,
    D_GPS_ALT double null,
    D_LOC_CITY varchar(1024) null,
    D_LOC_COUNTRY varchar(512) null,
    D_TITLE varchar(1024) null,
    D_CAPTION varchar(4096) null,
    D_MAKE varchar(256) null,
    D_MODEL varchar(256) null,
    D_UPDATED boolean not null,

    primary key (D_ID)
);

create table PLACE
(
    P_ID bigint auto_increment not null,
    P_PATH varchar(2048) not null,
    P_D_ID varchar(32) not null,
    P_L_ID bigint not null,

    primary key (P_ID)
);


alter table PLACE add constraint P_D_ID_FK foreign key (P_D_ID) references DESCRIPTION(D_ID);
alter table PLACE add constraint P_L_ID_FK foreign key (P_L_ID) references PLACE(P_ID);
