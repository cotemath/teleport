-- A few statements to populate our in-memory database

INSERT INTO User (uuid,openId,username,password,firstname,lastname,email,teleportationCredits) VALUES ('45678','https://plus.google.com/109571248969653749454','joe','test','Joe','Smith','js@thisisafakedomain.com',0);
INSERT INTO User (uuid,openId,username,password,firstname,lastname,email,teleportationCredits) VALUES ('98765','','janedoe','abc123','Jane','Doe','jd@thisisafakedomain.com',1000);

INSERT INTO TeleportLocation (id,city,country,latitude,longitude) VALUES (1,'Montreal','Canada',45.5017,73.5673);
INSERT INTO TeleportLocation (id,city,country,latitude,longitude) VALUES (2,'New York','USA',40.7128,74.0059);
INSERT INTO TeleportLocation (id,city,country,latitude,longitude) VALUES (3,'Boston','USA',42.3601,71.0589);
INSERT INTO TeleportLocation (id,city,country,latitude,longitude) VALUES (4,'Los Angeles','USA',34.0522,118.2437);
INSERT INTO TeleportLocation (id,city,country,latitude,longitude) VALUES (5,'Mexico City','Mexico',23.6345,102.5528);
INSERT INTO TeleportLocation (id,city,country,latitude,longitude) VALUES (6,'Havana','Cuba',23.1136,82.3666);
