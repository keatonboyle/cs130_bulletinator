#Test Data
INSERT INTO Bulletin (title, bodytext, shortdesc, contact, category, expiration) VALUES('Spring Sing', 'Join us for a show unlike any other!', "UCLA's Greatest Musical Tradition", 'springsing@alumni.ucla.edu', 'Events', '2013-05-18');

INSERT INTO File (extension) VALUES('jpg');

INSERT INTO File_Bulletin VALUES(1,1);


INSERT INTO Bulletin (title, bodytext, shortdesc, contact, category, expiration) VALUES('CEC Free Film: Identity Thief', 'Mild-mannered businessman Sandy Patterson travels from Denver to Miami to confront the deceptively harmless-looking woman who has been living it up after stealing Sandy\'s identity.', "brought to you by Campus Events Commission", 'http://campusevents.ucla.edu/', 'Events', '2013-06-04');


INSERT INTO Building (name) VALUES('Boelter Hall');
INSERT INTO Rectangle (north, south, east, west) VALUES(45.3414, 30, 45.3421, 30.001);
INSERT INTO Building_Rectangle VALUES(1,1);

INSERT INTO Building (name) VALUES('Math Sciences');
INSERT INTO Rectangle (north, south, east, west) VALUES(44.3414, 30, 44.3421, 30.001);
INSERT INTO Building_Rectangle VALUES(2,2);

INSERT INTO Building (name) VALUES('Young Hall');
INSERT INTO Rectangle (north, south, east, west) VALUES(45.3414, 31, 45.3421, 31.001);
INSERT INTO Building_Rectangle VALUES(3,3);

INSERT INTO Building (name) VALUES('Anderson School of Management');
INSERT INTO Rectangle (north, south, east, west) VALUES(44.3414, 31, 44.3421, 31.001);
INSERT INTO Building_Rectangle VALUES(4,4);

INSERT INTO Building (name) VALUES('Schoenberg Music Building');
INSERT INTO Rectangle (north, south, east, west) VALUES(43.3414, 30, 43.3421, 30.001);
INSERT INTO Building_Rectangle VALUES(5,5);

INSERT INTO Building (name) VALUES('Physics and Astronomy');
INSERT INTO Rectangle (north, south, east, west) VALUES(45.3414, 32, 45.3421, 32.001);
INSERT INTO Building_Rectangle VALUES(6,6);

INSERT INTO Building (name) VALUES('Perloff Hall');
INSERT INTO Rectangle (north, south, east, west) VALUES(43.3414, 31, 43.3421, 31.001);
INSERT INTO Building_Rectangle VALUES(7,7);

INSERT INTO Building (name) VALUES('Dodd Hall');
INSERT INTO Rectangle (north, south, east, west) VALUES(44.3414, 32, 44.3421, 32.001);
INSERT INTO Building_Rectangle VALUES(8,8);

INSERT INTO Building (name) VALUES('Humanities');
INSERT INTO Rectangle (north, south, east, west) VALUES(43.3414, 32, 43.3421, 32.001);
INSERT INTO Building_Rectangle VALUES(9,9);

INSERT INTO Building (name) VALUES('Royce Hall');
INSERT INTO Rectangle (north, south, east, west) VALUES(45.3414, 33, 45.3421, 33.001);
INSERT INTO Building_Rectangle VALUES(10,10);

INSERT INTO Building (name) VALUES('Broad Art Center');
INSERT INTO Rectangle (north, south, east, west) VALUES(46.3414, 30, 46.3421, 30.001);
INSERT INTO Building_Rectangle VALUES(11,11);

INSERT INTO Bulletin_Building VALUES(1,1);
INSERT INTO Bulletin_Building VALUES(1,2);
INSERT INTO Bulletin_Building VALUES(1,3);
INSERT INTO Bulletin_Building VALUES(1,4);
INSERT INTO Bulletin_Building VALUES(1,5);
INSERT INTO Bulletin_Building VALUES(1,6);
INSERT INTO Bulletin_Building VALUES(1,7);
INSERT INTO Bulletin_Building VALUES(1,8);
INSERT INTO Bulletin_Building VALUES(1,9);
INSERT INTO Bulletin_Building VALUES(1,10);
INSERT INTO Bulletin_Building VALUES(1,11);

INSERT INTO Bulletin_Building VALUES(2,1);
INSERT INTO Bulletin_Building VALUES(2,2);
INSERT INTO Bulletin_Building VALUES(2,3);
INSERT INTO Bulletin_Building VALUES(2,4);
INSERT INTO Bulletin_Building VALUES(2,5);
INSERT INTO Bulletin_Building VALUES(2,6);
INSERT INTO Bulletin_Building VALUES(2,7);
INSERT INTO Bulletin_Building VALUES(2,8);
INSERT INTO Bulletin_Building VALUES(2,9);
INSERT INTO Bulletin_Building VALUES(2,10);
INSERT INTO Bulletin_Building VALUES(2,11);