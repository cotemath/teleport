-- A few statements to populate our in-memory database

INSERT INTO User (id,username,firstname,lastname,email,teleportationCredits) VALUES (1,'js','John','Smith','js@thisisafakedomain.com',0);
INSERT INTO User (id,username,firstname,lastname,email,teleportationCredits) VALUES (2,'janedoe','Jane','Doe','jd@thisisafakedomain.com',1000);

INSERT INTO TeleportLocation (id,city,country,latitude,longitude) VALUES (1,'Montreal','Canada',45.5017,73.5673);
INSERT INTO TeleportLocation (id,city,country,latitude,longitude) VALUES (2,'New York','USA',40.7128,74.0059);
INSERT INTO TeleportLocation (id,city,country,latitude,longitude) VALUES (3,'Boston','USA',42.3601,71.0589);
