DROP TABLE CONTACT_T;
CREATE TABLE CONTACT_T(
  CONTACT_NO NUMBER             NOT NULL,
  NAME       VARCHAR2(100 BYTE) NOT NULL,
  MOBILE     VARCHAR2(20 BYTE),
  EMAIL      VARCHAR2(100 BYTE),
  ADDRESS    VARCHAR2(200 BYTE),
  CREATED_AT VARCHAR2(20 BYTE),
  CONSTRAINT PK_CONTACT PRIMARY KEY(CONTACT_NO)
);

DROP SEQUENCE CONTACT_SEQ;
CREATE SEQUENCE CONTACT_SEQ NOCACHE;

SELECT * FROM CONTACT_T;