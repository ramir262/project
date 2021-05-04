INSERT INTO
	Account (AccountId, Email, Hash)
VALUES
	(1,"demo@chapman.edu","none");
INSERT INTO
	Profile (AccountId, Username, Picture)
VALUES
	(1,"demo","empty");
INSERT INTO
	Graduation (AccountId, Year, Semester, Graduated)
VALUES
	(1,"2020","Spring",1);


INSERT INTO
	Review (ReviewId, Stars, Edit)
VALUES
	(1,5,20210325204516);

INSERT INTO
	Post (ReviewId, AccountId, ClassId, Creation)
VALUES
	(1,1,1,20210325204516);

INSERT INTO
	Questions (QuestionId, Question)
VALUES
	(4,"Assignments:");

INSERT INTO
	Responses (ReviewId, QuestionId, Response, Edit)
VALUES
	(1,4,"Tough assignments. Learned a lot!",20210325204516);

INSERT INTO
	Questions (QuestionId, Question)
VALUES
	(5,"Exams:");

INSERT INTO
	Responses (ReviewId, QuestionId, Response, Edit)
VALUES
	(1,5,"Class prepared well for the test.",20210325204516);
