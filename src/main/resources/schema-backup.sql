CREATE TABLE TICKET_TYPE
(
    ID   INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NAME VARCHAR(15) NOT NULL
);

CREATE TABLE TICKET
(
    ID              INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    TYPE_ID         INT         NOT NULL,
    EVENT_NAME      VARCHAR(50) NOT NULL,
    VENUE           VARCHAR(50) NOT NULL,
    DESCRIPTION     VARCHAR(255),
    EVENT_DATE_TIME TIMESTAMP,
    PRICE           DECIMAL(6, 2),
    FOREIGN KEY (TYPE_ID) REFERENCES TICKET_TYPE (ID)
);