CREATE database USER;

CREATE TABLE BBS(
     bbsNo INT,
     bbsGn INT,
     bbsPn INT,
     bbsLevel INT,
     
     bbsTitle VARCHAR(50),
     userID VARCHAR(20),
     bbsDate DATETIME,
     bbsContent VARCHAR(2048),
     bbsAvailable INT,
     PRIMARY KEY(bbsNo)
     );