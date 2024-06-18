INSERT INTO TICKET_TYPE(NAME) VALUES('SPORT');
INSERT INTO TICKET_TYPE(NAME) VALUES('MUSIC');
INSERT INTO TICKET_TYPE(NAME) VALUES('ART');
INSERT INTO TICKET_TYPE(NAME) VALUES('CINEMA');
INSERT INTO TICKET_TYPE(NAME) VALUES('ENTERTAINMENT');
INSERT INTO TICKET_TYPE(NAME) VALUES('TRAVEL');

INSERT INTO TICKET(TYPE_ID, EVENT_NAME, VENUE, DESCRIPTION, EVENT_DATE_TIME, PRICE)
VALUES(1, 'CROATIA - TUNISIA', 'CAIRO', 'INTERNATIONAL FRIENDLY GAME',
       parsedatetime('24-03-2024 12:00', 'dd-MM-yyyy hh:mm'), 50);

INSERT INTO TICKET(TYPE_ID, EVENT_NAME, VENUE, DESCRIPTION, EVENT_DATE_TIME, PRICE)
VALUES(2, 'INMUSIC FESTIVAL', 'ZAGREB', 'SUMMER MUSIC FESTIVAL',
       parsedatetime('24-06-2024 12:00', 'dd-MM-yyyy hh:mm'), 100);

INSERT INTO TICKET(TYPE_ID, EVENT_NAME, VENUE, DESCRIPTION, EVENT_DATE_TIME, PRICE)
VALUES(3, 'NOĆ MUZEJA', 'CROATIA', 'MUSEUMS NIGHT',
       parsedatetime('24-11-2024 12:00', 'dd-MM-yyyy hh:mm'), 0);


insert into USERS(id, username, password)
values
    (1, 'user', '$2a$12$h0HcS2QDb/7zPASbLa2GoOTSRP6CWK0oX7pCK.dPjkM6L5N4pNovi'), -- password = user
    (2, 'admin', '$2a$12$INo0nbj40sQrTB7b28KJput/bNltGmFyCfRsUhvy73qcXo5/XdsTG'); -- password = admin

 insert into roles (id, name)
 values
     (1, 'ROLE_ADMIN'),
     (2, 'ROLE_USER');

 insert into users_roles (user_id, role_id)
 values
     (1, 2),
     (2, 1);