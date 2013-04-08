drop table FILEPATH if exists cascade constraints;
drop table DESCRIPTION if exists cascade constraints;
drop table PERSON if exists cascade constraints;
drop table PLACE if exists cascade constraints;


create table PLACE
(
    PL_ID bigint auto_increment not null,
    PL_NAME varchar(1024) not null,
    PL_GPS_LAT double not null,
    PL_GPS_LNG double not null,
    PL_RADIUS double not null,

    primary key (PL_ID)
);

create table PERSON
(
    PE_ID bigint auto_increment not null,
    PE_NAME varchar(1024) not null,

    primary key (PE_ID)
);

create table DESCRIPTION
(
    D_ID varchar(32) not null,
    D_TYPE varchar(32) not null,
    D_CREATED_DATE timestamp null,
    D_GPS_LAT double null,
    D_GPS_LNG double null,
    D_PL_ID bigint null,
    D_TITLE varchar(1024) null,
    D_MAKE varchar(256) null,
    D_MODEL varchar(256) null,
    D_PE_ID bigint null,
    D_UPDATED boolean not null,
    D_IGNORE_SORT boolean not null,
    D_FILES_COUNT int not null,

    primary key (D_ID)
);

create table FILEPATH
(
    FP_ID bigint auto_increment not null,
    FP_PATH varchar(2048) not null,
    FP_NAME varchar(1024) not null,
    FP_D_ID varchar(32) not null,

    primary key (FP_ID)
);


alter table FILEPATH add constraint FP_D_ID_FK foreign key (FP_D_ID) references DESCRIPTION(D_ID);
alter table DESCRIPTION add constraint D_PL_ID_FK foreign key (D_PL_ID) references PLACE(PL_ID);
alter table DESCRIPTION add constraint D_PE_ID_FK foreign key (D_PE_ID) references PERSON(PE_ID);
