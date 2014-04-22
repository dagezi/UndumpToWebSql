PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE __WebKitDatabaseInfoTable__ (key TEXT NOT NULL ON CONFLICT FAIL UNIQUE ON CONFLICT REPLACE,value TEXT NOT NULL ON CONFLICT FAIL);
INSERT INTO "__WebKitDatabaseInfoTable__" VALUES('WebKitDatabaseVersionKey','1');
CREATE TABLE prefecture (id UNIQUE, name, capital);
INSERT INTO prefecture VALUES(1,'Hokkaido', 'Sapporo');
INSERT INTO prefecture VALUES(2,'Aomori', 'Aomori');
COMMIT;
