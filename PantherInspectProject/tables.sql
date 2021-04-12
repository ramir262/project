

CREATE TABLE IF NOT EXISTS Account (
	AccountId int,
	Email varchar(50),
	Hash varchar(100),
	CHECK (Email LIKE "%@%chapman.edu"),
	PRIMARY KEY(AccountId)
) ;

CREATE TABLE IF NOT EXISTS Profile (
	AccountId int,
	Username varchar(20),
	Picture varchar(100),
	FOREIGN KEY(AccountId) REFERENCES Account(AccountId) ON DELETE CASCADE,
	PRIMARY KEY(AccountId)
) ;

CREATE TABLE IF NOT EXISTS Graduation (
	AccountId int,
	Year int,
	Semester varchar(8),
	Graduated boolean,
	CHECK (Year <= 9999),
	FOREIGN KEY(AccountId) REFERENCES Account(AccountId) ON DELETE CASCADE,
	PRIMARY KEY(AccountId)
) ;

CREATE TABLE IF NOT EXISTS Course (
	CourseId int,
	Subject varchar(30),
	CourseNum int,
	CName varchar(40),
	PRIMARY KEY(CourseId)
) ;

CREATE TABLE IF NOT EXISTS Professor (
	ProfessorId int,
	PName varchar(30),
	PRIMARY KEY(ProfessorId)
) ;

CREATE TABLE IF NOT EXISTS Class (
	ClassId int,
	CourseId int,
	ProfessorId int,
	FOREIGN KEY(CourseId) REFERENCES Course(CourseId) ON DELETE CASCADE,
	FOREIGN KEY(ProfessorId) REFERENCES Professor(ProfessorId) ON DELETE CASCADE,
	PRIMARY KEY(ClassId)
) ;

CREATE TABLE IF NOT EXISTS Review (
	ReviewId int,
	Stars int,
	Edit timestamp,
	CHECK (Stars <= 5 AND Stars > 0),
	PRIMARY KEY(ReviewId)
) ;

CREATE TABLE IF NOT EXISTS Questions (
	QuestionId int,
	Question varchar(100),
	PRIMARY KEY(QuestionId)
) ;

CREATE TABLE IF NOT EXISTS Responses (
	ReviewId int,
	QuestionId int,
	Response varchar(200),
	Edit timestamp,
	FOREIGN KEY(ReviewId) REFERENCES Review(ReviewId) ON DELETE CASCADE,
	FOREIGN KEY(QuestionId) REFERENCES Questions(QuestionId) ON DELETE CASCADE,
	PRIMARY KEY(ReviewId, QuestionId)
) ;

CREATE TABLE IF NOT EXISTS Post (
	ReviewId int,
	AccountId int,
	ClassId int,
	Creation timestamp,
	FOREIGN KEY(ReviewId) REFERENCES Review(ReviewId) ON DELETE CASCADE,
	FOREIGN KEY(AccountId) REFERENCES Account(AccountId) ON DELETE CASCADE,
	FOREIGN KEY(ClassId) REFERENCES Class(ClassId) ON DELETE CASCADE,
	PRIMARY KEY(ReviewId)
) ;

CREATE TABLE IF NOT EXISTS Security (
	AccountId int,
	QuestionId int,
	Hash varchar(100),
	FOREIGN KEY(AccountId) REFERENCES Account(AccountId) ON DELETE CASCADE,
	FOREIGN KEY(QuestionId) REFERENCES Questions(QuestionId) ON DELETE CASCADE,
	PRIMARY KEY(AccountId, QuestionId)
) ;

INSERT INTO
	Questions (QuestionId, Question)
VALUES
	(1, "What is your mother's maiden name?")
ON DUPLICATE KEY UPDATE Question = VALUES (Question);
INSERT INTO
	Questions (QuestionId, Question)
VALUES
	(2, "What is the name of your first pet?")
ON DUPLICATE KEY UPDATE Question = VALUES (Question);
INSERT INTO
	Questions (QuestionId, Question)
VALUES
	(3, "What was your favorite childhood TV show?")
ON DUPLICATE KEY UPDATE Question = VALUES (Question);

INSERT INTO
	Questions (QuestionId, Question)
VALUES
	(4, "How well did the lectures prepare you for the exams?")
ON DUPLICATE KEY UPDATE Question = VALUES (Question);
INSERT INTO
	Questions (QuestionId, Question)
VALUES
	(5, "Were assignments beneficial to your learning?")
ON DUPLICATE KEY UPDATE Question = VALUES (Question);
INSERT INTO
	Questions (QuestionId, Question)
VALUES
	(6, "What was the easiest aspect of this course?")
ON DUPLICATE KEY UPDATE Question = VALUES (Question);
INSERT INTO
	Questions (QuestionId, Question)
VALUES
	(7, "What was the most challenging aspect of this course?")
ON DUPLICATE KEY UPDATE Question = VALUES (Question);
INSERT INTO
	Questions (QuestionId, Question)
VALUES
	(8, "Did you learn as much as you expected from this course?")
ON DUPLICATE KEY UPDATE Question = VALUES (Question);
INSERT INTO
	Questions (QuestionId, Question)
VALUES
	(9, "How could this course be improved?")
ON DUPLICATE KEY UPDATE Question = VALUES (Question);
INSERT INTO
  Questions (QuestionId,Question)
VALUES
	(10, "Any additional comments or concerns about the class?")
ON DUPLICATE KEY UPDATE Question = VALUES (Question);
