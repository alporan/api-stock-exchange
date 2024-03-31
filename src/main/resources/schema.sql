-- DDL script
CREATE TABLE APP_USER (
    ID NUMBER(19) PRIMARY KEY,
    USERNAME VARCHAR2(20) NOT NULL,
    PASSWORD VARCHAR2(200) NOT NULL
);
CREATE SEQUENCE APP_USER_ID_SEQ MINVALUE 1 MAXVALUE 999999999999999999 START WITH 1 INCREMENT BY 1 CACHE 20;
CREATE UNIQUE INDEX PK_APP_USER ON APP_USER (ID);
CREATE UNIQUE INDEX UC_APP_USER_USERNAME ON APP_USER (USERNAME);
ALTER TABLE APP_USER ADD CONSTRAINT UC_APP_USER_USERNAME UNIQUE (USERNAME);


CREATE TABLE ROLE (
    ID NUMBER(19) PRIMARY KEY,
    AUTHORITY VARCHAR2(10) NOT NULL
);
CREATE SEQUENCE ROLE_ID_SEQ MINVALUE 1 MAXVALUE 999999999999999999 START WITH 1 INCREMENT BY 1 CACHE 20;
CREATE UNIQUE INDEX PK_ROLE ON ROLE (ID);
CREATE UNIQUE INDEX UC_ROLE_AUTHORITY ON ROLE (AUTHORITY);
ALTER TABLE ROLE ADD CONSTRAINT UC_ROLE_AUTHORITY UNIQUE (AUTHORITY);
ALTER TABLE ROLE ADD CONSTRAINT CC_ROLE_AUTHORITY CHECK (AUTHORITY) IN ('ADMIN', 'USER');


CREATE TABLE STOCK (
    ID NUMBER(19) PRIMARY KEY,
    NAME VARCHAR2(5) NOT NULL,
    DESCRIPTION VARCHAR2(100) NOT NULL,
    CURRENT_PRICE NUMBER(32,5) NOT NULL,
    LAST_UPDATE TIMESTAMP NOT NULL
);
CREATE SEQUENCE STOCK_ID_SEQ MINVALUE 1 MAXVALUE 999999999999999999 START WITH 1 INCREMENT BY 1 CACHE 20;
CREATE UNIQUE INDEX PK_STOCK ON STOCK (ID);
CREATE UNIQUE INDEX UC_STOCK_NAME ON STOCK (NAME);
ALTER TABLE STOCK ADD CONSTRAINT UC_STOCK_NAME UNIQUE (NAME);
ALTER TABLE STOCK ADD CONSTRAINT CC_CURRENT_PRICE_MIN check (CURRENT_PRICE >= 0);


CREATE TABLE STOCK_EXCHANGE (
    ID NUMBER(19) PRIMARY KEY,
    NAME VARCHAR2(50) NOT NULL,
    DESCRIPTION VARCHAR2(100) NOT NULL,
    LIVE_IN_MARKET NUMBER(1) NOT NULL
);
CREATE SEQUENCE STOCK_EXCHANGE_ID_SEQ MINVALUE 1 MAXVALUE 999999999999999999 START WITH 1 INCREMENT BY 1 CACHE 20;
CREATE UNIQUE INDEX PK_STOCK_EXCHANGE ON STOCK_EXCHANGE (ID);
CREATE UNIQUE INDEX UC_STOCK_EXCHANGE_NAME ON STOCK (NAME);
ALTER TABLE STOCK_EXCHANGE ADD CONSTRAINT UC_STOCK_EXCHANGE_NAME UNIQUE (NAME);


CREATE TABLE APP_USER_ROLE (
    ID NUMBER(19) PRIMARY KEY,
    APP_USER_ID NUMBER(19) NOT NULL,
    ROLE_ID NUMBER(19) NOT NULL
);
CREATE SEQUENCE APP_USER_ROLE_ID_SEQ MINVALUE 1 MAXVALUE 999999999999999999 START WITH 1 INCREMENT BY 1 CACHE 20;
CREATE UNIQUE INDEX PK_APP_USER_ROLE ON APP_USER_ROLE (ID);
CREATE UNIQUE INDEX UC_APP_USER_ID_ROLE_ID ON APP_USER_ROLE (APP_USER_ID, ROLE_ID);
ALTER TABLE APP_USER_ROLE ADD CONSTRAINT FK_APP_USER_ID FOREIGN KEY (APP_USER_ID) REFERENCES APP_USER (ID);
ALTER TABLE APP_USER_ROLE ADD CONSTRAINT FK_ROLE_ID FOREIGN KEY (ROLE_ID) REFERENCES ROLE (ID);
ALTER TABLE APP_USER_ROLE ADD CONSTRAINT UC_APP_USER_ID_ROLE_ID UNIQUE (APP_USER_ID, ROLE_ID);


CREATE TABLE STOCK_EXCHANGE_STOCK (
    ID NUMBER(19) PRIMARY KEY,
    STOCK_EXCHANGE_ID NUMBER(19) NOT NULL,
    STOCK_ID NUMBER(19) NOT NULL
);
CREATE SEQUENCE STOCK_EXCHANGE_STOCK_ID_SEQ MINVALUE 1 MAXVALUE 999999999999999999 START WITH 1 INCREMENT BY 1 CACHE 20;
CREATE UNIQUE INDEX PK_STOCK_EXCHANGE_STOCK ON STOCK_EXCHANGE_STOCK (ID);
CREATE UNIQUE INDEX UC_STOCK_EXCHANGE_ID_STOCK_ID ON STOCK_EXCHANGE_STOCK (STOCK_EXCHANGE_ID, STOCK_ID);
ALTER TABLE STOCK_EXCHANGE_STOCK ADD CONSTRAINT FK_STOCK_EXCHANGE_ID FOREIGN KEY (STOCK_EXCHANGE_ID) REFERENCES STOCK_EXCHANGE (ID);
ALTER TABLE STOCK_EXCHANGE_STOCK ADD CONSTRAINT FK_STOCK_ID FOREIGN KEY (STOCK_ID) REFERENCES STOCK (ID);
ALTER TABLE STOCK_EXCHANGE_STOCK ADD CONSTRAINT UC_STOCK_EXCHANGE_ID_STOCK_ID UNIQUE (STOCK_EXCHANGE_ID, STOCK_ID);
