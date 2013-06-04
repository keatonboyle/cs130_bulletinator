CREATE TABLE Bulletin (
   bulletin_id bigint PRIMARY KEY AUTO_INCREMENT,
   title varchar(30) NOT NULL,
   bodytext text,
   shortdesc varchar(75) NOT NULL,
   contact varchar(50),
   category varchar(20),
   expiration date
);

CREATE TABLE File (
   file_id bigint PRIMARY KEY AUTO_INCREMENT,
   extension varchar(5) NOT NULL
);

CREATE TABLE File_Bulletin (
   file_id bigint PRIMARY KEY,
   bulletin_id bigint,
   UNIQUE (bulletin_id),
   FOREIGN KEY (file_id) references File(file_id),
   FOREIGN KEY (bulletin_id) references Bulletin(bulletin_id)
);

CREATE TABLE Creator (
   username varchar(30) PRIMARY KEY,
   email varchar(50) NOT NULL,
   password varchar(30) NOT NULL
);
   
CREATE TABLE Building (
   building_id bigint PRIMARY KEY AUTO_INCREMENT,
   name varchar(50)
);
   
CREATE TABLE Rectangle (
   rectangle_id bigint PRIMARY KEY AUTO_INCREMENT,
   north float NOT NULL,
   south float NOT NULL,
   east float NOT NULL,
   west float NOT NULL
);
   
CREATE TABLE Building_Rectangle (
   rectangle_id bigint,
   building_id bigint,
   PRIMARY KEY (rectangle_id, building_id),
   FOREIGN KEY (rectangle_id) references Rectangle(rectangle_id),
   FOREIGN KEY (building_id) references Building(building_id)
);
   
CREATE TABLE Bulletin_Building (
   bulletin_id bigint,
   building_id bigint,
   PRIMARY KEY (bulletin_id, building_id),
   FOREIGN KEY (bulletin_id) references Bulletin(bulletin_id),
   FOREIGN KEY (building_id) references Building(building_id)
);
   
   
CREATE TABLE Bulletin_Creator (
   bulletin_id bigint PRIMARY KEY,
   username varchar(30) NOT NULL,
   FOREIGN KEY (bulletin_id) references Bulletin(bulletin_id),
   FOREIGN KEY (username) references Creator(username)
);