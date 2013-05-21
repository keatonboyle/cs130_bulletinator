#Test Data
INSERT INTO Bulletin VALUES(1, 'Spring Sing', 'Join us for a show unlike any other!', "UCLA's Greatest Musical Tradition", 'springsing@alumni.ucla.edu', 'Events', '2013-05-18');

#Test Data
INSERT INTO File VALUES(1, 'jpg');

#Test Data
INSERT INTO FileToBulletin VALUES(1,1);

#UPDATE due to Test Bulletin;
UPDATE MaxID SET btnid=2, fid=2;