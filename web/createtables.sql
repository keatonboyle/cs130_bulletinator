CREATE TABLE Bulletin (
   btnid int PRIMARY KEY,
   title varchar(30) NOT NULL,
   bodytext varchar(500),
   shortdesc varchar(75) NOT NULL,
   contact varchar(50),
   category varchar(20),
   expiration date
);

CREATE TABLE File (
   fid int PRIMARY KEY,
   ext varchar(5) NOT NULL
);

CREATE TABLE FileToBulletin (
   fid int PRIMARY KEY,
   btnid int,
   UNIQUE (btnid),
   FOREIGN KEY (fid) references File(fid),
   FOREIGN KEY (btnid) references Bulletin(btnid)
);

CREATE TABLE MaxID (
   btnid int NOT NULL,
   fid int NOT NULL,
   bldid int NOT NULL,
   rid int NOT NULL
);

INSERT INTO MaxID VALUES(1,1,1,1);

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
