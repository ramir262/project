INSERT INTO
	Course (CourseId,Subject,CourseNum, CName)
VALUES
	(1,"Computer Science", 230, "Computer Science I");

INSERT INTO
	Course (CourseId,Subject,CourseNum, CName)
VALUES
	(2,"Computer Science", 231, "Computer Science II");

INSERT INTO
	Course (CourseId,Subject,CourseNum, CName)
VALUES
	(3,"Software Engineering", 310, "Software Design");

INSERT INTO
	Course (CourseId,Subject,CourseNum, CName)
VALUES
	(4,"Computer Science", 402, "Compiler Construction");

INSERT INTO
	Course (CourseId,Subject,CourseNum, CName)
VALUES
	(5,"Computer Science", 330, "Digital Logic I");

INSERT INTO
	Course (CourseId,Subject,CourseNum, CName)
VALUES
	(6,"Computer Science", 408, "Database Management");


INSERT INTO
	Professor (ProfessorId,PName)
VALUES
	(1,"Nicholas LaHaye");

INSERT INTO
	Professor (ProfessorId,PName)
VALUES
	(2,"Derek Prate");

INSERT INTO
	Professor (ProfessorId,PName)
VALUES
	(3,"Christopher Boyd");
INSERT INTO
	Professor (ProfessorId,PName)
VALUES
	(4,"Peiyo Zhao");

INSERT INTO
	Professor (ProfessorId,PName)
VALUES
	(5,"Erik Linstead");

INSERT INTO
	Class (ClassId,CourseId,ProfessorId)
VALUES
	(1,1,1);

INSERT INTO
	Class (ClassId,CourseId,ProfessorId)
VALUES
	(2,1,3);

INSERT INTO
	Class (ClassId,CourseId,ProfessorId)
VALUES
	(3,2,1);

INSERT INTO
	Class (ClassId,CourseId,ProfessorId)
VALUES
	(4,2,3);

INSERT INTO
	Class (ClassId,CourseId,ProfessorId)
VALUES
	(5,3,2);
INSERT INTO
	Class (ClassId,CourseId,ProfessorId)
VALUES
	(6,5,4);
INSERT INTO
	Class (ClassId,CourseId,ProfessorId)
VALUES
	(6,6,5);
