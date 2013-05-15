CREATE TABLE Bulletin (
   btnid int PRIMARY KEY,
   title varchar(30) NOT NULL,
   bodytext varchar(500),
   shortdesc varchar(75) NOT NULL,
   contact varchar(50),
   category varchar(20),
   expiration date
);

#Test Data
INSERT INTO Bulletin VALUES(1, 'Spring Sing', 'Join us for a show unlike any other!', "UCLA's Greatest Musical Tradition", 'springsing@alumni.ucla.edu', 'Events', '2013-05-18');

CREATE TABLE File (
   fid int PRIMARY KEY,
   ext varchar(5) NOT NULL
);

#Test Data
INSERT INTO File VALUES(1, 'jpg');

CREATE TABLE FileToBulletin (
   fid int PRIMARY KEY,
   btnid int,
   UNIQUE (btnid),
   FOREIGN KEY (fid) references File(fid),
   FOREIGN KEY (btnid) references Bulletin(btnid)
);

#Test Data
INSERT INTO FileToBulletin VALUES(1,1);

CREATE TABLE MaxID (
   btnid int,
   fid int
);

INSERT INTO MaxID VALUES(1,1);

#UPDATE due to Test Bulletin;
UPDATE MaxID SET btnid=2, fid=2;

CREATE TABLE Creator (
   username varchar(30) PRIMARY KEY,
   email varchar(50) NOT NULL,
   password varchar(30) NOT NULL
);
   
CREATE TABLE Building (
   bldid int PRIMARY KEY,
   name varchar(50)
);
   
CREATE TABLE Rectangle (
   rid int PRIMARY KEY,
   north float NOT NULL,
   south float NOT NULL,
   east float NOT NULL,
   west float NOT NULL
);
   
CREATE TABLE RectangleToBuilding (
   rid int PRIMARY KEY,
   bldid int,
   UNIQUE (bldid),
   FOREIGN KEY (rid) references Rectangle(rid),
   FOREIGN KEY (bldid) references Building(bldid)
);
   
CREATE TABLE BulletinToBuilding (
   btnid int,
   bldid int,
   PRIMARY KEY (btnid, bldid),
   FOREIGN KEY (btnid) references Bulletin(btnid),
   FOREIGN KEY (bldid) references Building(bldid)
);
   
   
CREATE TABLE BulletinToCreator (
   btnid int PRIMARY KEY,
   username varchar(30) NOT NULL,
   FOREIGN KEY (btnid) references Bulletin(btnid),
   FOREIGN KEY (username) references Creator(username)
);
