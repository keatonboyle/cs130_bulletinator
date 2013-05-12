CREATE TABLE Bulletin (
   bid integer PRIMARY KEY,
   title varchar(30) NOT NULL,
   bodytext varchar(500),
   description varchar(75) NOT NULL,
   contact varchar(50),
   imageid integer,
   category varchar(20),
   expiration date
);

CREATE TABLE MaxID (
   bulletinid int,
   imageid int
);

INSERT INTO MaxID VALUES(1,1);

CREATE TABLE Creator (
   username varchar(30) PRIMARY KEY,
   email varchar(50) NOT NULL,
   password varchar(30) NOT NULL
);
   
CREATE TABLE Building (
   name varchar(50) PRIMARY KEY,
   location varchar(200) NOT NULL
);
   
CREATE TABLE Rectangle (
   rid integer PRIMARY KEY,
   north float NOT NULL,
   south float NOT NULL,
   east float NOT NULL,
   west float NOT NULL
);
   
CREATE TABLE RectangleBuilding (
   rid integer PRIMARY KEY,
   buildingname varchar(50),
   UNIQUE (buildingname),
   FOREIGN KEY (rid) references Rectangle(rid),
   FOREIGN KEY (buildingname) references Building(name)
);
   
CREATE TABLE ExistsIn (
   bid integer,
   buildingname varchar(50),
   PRIMARY KEY (bid, buildingname),
   FOREIGN KEY (bid) references Bulletin(bid),
   FOREIGN KEY (buildingname) references Building(name)
);
   
   
CREATE TABLE Creates (
   bid integer PRIMARY KEY,
   username varchar(30) NOT NULL,
   FOREIGN KEY (bid) references Bulletin(bid),
   FOREIGN KEY (username) references Creator(username)
);
