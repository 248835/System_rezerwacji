DROP TABLE IF EXISTS HOTELE  cascade;
DROP TABLE IF EXISTS POKOJE  cascade;
DROP TABLE IF EXISTS UZYTKOWNICY  cascade;
DROP TABLE IF EXISTS KIEROWNICY  cascade;
DROP TABLE IF EXISTS REZERWACJE  cascade;

DROP VIEW IF EXISTS SPIS_REZERWACJI;
DROP VIEW IF EXISTS SPIS_KLIENTOW;

CREATE TABLE  UZYTKOWNICY
(
 NAZWA VARCHAR(40) PRIMARY KEY,
 HASLO VARCHAR(100) NOT NULL,
 IMIE  VARCHAR(20) NOT NULL,
 NAZWISKO  VARCHAR(40) NOT NULL,
 ROLA VARCHAR(20) NOT NULL,
 NAZWA_KIEROWNIKA VARCHAR(40),
 foreign key (NAZWA_KIEROWNIKA) references UZYTKOWNICY(NAZWA) on delete cascade on update cascade
);

CREATE TABLE  HOTELE
(
 ID integer PRIMARY KEY auto_increment,
 NAZWA  VARCHAR(40) NOT NULL,
 MIASTO  VARCHAR(60) NOT NULL,
 ULICA VARCHAR(60) NOT NULL,
 OCENA  SMALLINT NOT NULL,
 NAZWA_KIEROWNIKA VARCHAR(40) NOT NULL,
 foreign key (NAZWA_KIEROWNIKA) REFERENCES UZYTKOWNICY(NAZWA) on delete cascade on update cascade
);

CREATE TABLE  POKOJE
(
 ID  integer PRIMARY KEY auto_increment,
 NUMER  SMALLINT, /*Najwiekszy hotel na swiecie ma 6118 pokoi*/
 RODZAJ  SMALLINT NOT NULL,
 ROZMIAR SMALLINT NOT NULL,
 CENA  NUMERIC(8,2),
 ID_HOTELU  INT NOT NULL,
 foreign key (ID_HOTELU) REFERENCES  HOTELE ( ID ) on delete cascade on update cascade,
 constraint pokoj unique (ID_HOTELU,NUMER)
);


CREATE TABLE  REZERWACJE
(
 ID integer PRIMARY KEY auto_increment,
 ID_POKOJU  INT NOT NULL,
 foreign key (ID_POKOJU) REFERENCES POKOJE(ID) on delete cascade on update cascade,
 NAZWA_KLIENTA  VARCHAR(40) NOT NULL,
 foreign key (NAZWA_KLIENTA) REFERENCES UZYTKOWNICY(NAZWA) on delete cascade on update cascade,
 DATA_ROZPOCZECIA  DATE NOT NULL,
 DATA_ZAKONCZENIA  DATE NOT NULL,
 CONSTRAINT CHECK_DATE
	CHECK ( DATA_ZAKONCZENIA >= DATA_ROZPOCZECIA )
);


CREATE VIEW SPIS_KLIENTOW (NAZWA_KLIENTA, IMIE_KLIENTA, NAZWISKO_KLIENTA) AS
SELECT
NAZWA,
IMIE,
NAZWISKO
FROM UZYTKOWNICY
WHERE ROLA LIKE 'KLIENT';

CREATE VIEW SPIS_REZERWACJI (NAZWA_HOTELU, MIASTO, ULICA, NUMER_POKOJU, RODZAJ_POKOJU,
ROZMIAR_POKOJU, IMIE_KLIENTA, NAZWISKO_KLIENTA, POCZATEK_REZERWACJI, KONIEC_REZERWACJI) AS
SELECT
(SELECT NAZWA FROM HOTELE H, POKOJE P WHERE H.ID=P.ID_HOTELU AND P.ID=R.ID_POKOJU),
(SELECT MIASTO FROM HOTELE H, POKOJE P WHERE H.ID=P.ID_HOTELU AND P.ID=R.ID_POKOJU),
(SELECT ULICA FROM HOTELE H, POKOJE P WHERE H.ID=P.ID_HOTELU AND P.ID=R.ID_POKOJU),
(SELECT NUMER FROM POKOJE P WHERE P.ID=R.ID_POKOJU),
(SELECT RODZAJ FROM POKOJE P WHERE P.ID=R.ID_POKOJU),
(SELECT ROZMIAR FROM POKOJE P WHERE P.ID=R.ID_POKOJU),
(SELECT IMIE FROM UZYTKOWNICY U WHERE U.NAZWA=R.NAZWA_KLIENTA),
(SELECT NAZWISKO FROM UZYTKOWNICY U WHERE U.NAZWA=R.NAZWA_KLIENTA),
R.DATA_ROZPOCZECIA,
R.DATA_ZAKONCZENIA
FROM
REZERWACJE R;

/*CREATE EXTENSION btree_gist;
ALTER TABLE REZERWACJE ADD CONSTRAINT free
EXCLUDE USING gist
    (ID_POKOJU WITH =,
    tsrange(data_rozpoczecia,data_zakonczenia) WITH &&);*/
