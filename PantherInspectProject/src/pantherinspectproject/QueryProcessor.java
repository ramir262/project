package pantherinspectproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;

public class QueryProcessor {

	String INSERT = "INSERT INTO %s ( %s ) VALUES ( %s );";
	String SELECT = "SELECT %s FROM %s %s ;";
	String UPDATE = "UPDATE %s SET %s %s ;";
	
	String WHERE = "WHERE %s";
	String MAX = "MAX( %s )";
	String AVG = "AVG( %s )";
	String NATURAL_JOIN = " NATURAL JOIN ";
	String AND = " AND ";
	String COMMA = ", ";

	Connection conn;
	Time time;

	public QueryProcessor(Connection conn) {
		this.conn = conn;
		this.time = new Time();
	}
	
	/*
	------------------------------------------------------------------------------------------
	Create Tables
	------------------------------------------------------------------------------------------
	*/
	/*
	-------------------------------
	function: createTables
	-------------------------------
	params: 
		String filename : name of file which contains predefined sql statements
						  (in this context, contains create table statements)
	purpose:
		process predefined sql file
		(create tables from predefined sql file)
	return:
		boolean success or failure
	*/
	public boolean createTables(String filename){
		File file = new File(filename);
		try {
			if (file.exists()) {
				System.out.println("Reading " + filename);
				ScriptRunner sr = new ScriptRunner(this.conn);
			    Reader reader = new BufferedReader(new FileReader(filename));
				
			    sr.setDelimiter(";");
			    sr.runScript(reader);
			    System.out.println(String.format("Completed execution of %s queries",filename));
			    return true;
			}
			else {
				System.out.println("Could not locate " + filename);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	------------------------------------------------------------------------------------------
	Insert Account Information
	------------------------------------------------------------------------------------------
	*/

	/*
	-------------------------------
	function: insertAccount
	-------------------------------
	purpose:
		insert values into account table
	return:
		boolean success or failure
	*/
	public boolean insertAccount(String accountId, String email, String hash) {
		String tableName = "Account";
		String columns = "AccountId, Email, Hash";
		String values = "?, ?, ?";
		String[] instance = new String[] {accountId, email, hash};
		boolean success = insert(tableName, columns, values, instance);
		return success;
	}
	/*
	-------------------------------
	function: insertGraduation
	-------------------------------
	purpose:
		insert values into graduation table
	return:
		boolean success or failure
	*/
	public boolean insertGraduation(String accountId, String year, String semester, String status) {
		String tableName = "Graduation";
		String columns = "AccountId, Year, Semester, Graduated";
		String values = "?, ?, ?, ?";
		String[] instance = new String[] {accountId, year, semester, status};
		boolean success = insert(tableName, columns, values, instance);
		return success;
	}

	/*
	-------------------------------
	function: insertProfile
	-------------------------------
	purpose:
		insert values into profile table
	return:
		boolean success or failure
	*/
	public boolean insertProfile(String accountId, String username, String picture) {
		String tableName = "Profile";
		String columns = "AccountId, Username, Picture";
		String values = "?, ?, ?";
		String[] instance = new String[] {accountId, username, picture};
		boolean success = insert(tableName, columns, values, instance);
		return success;
	}
	/*
	------------------------------------------------------------------------------------------
	Update Account Information
	------------------------------------------------------------------------------------------
	*/
	/*
	-------------------------------
	function: updateProfile
	-------------------------------
	purpose:
		update values in profile table
	return:
		boolean success or failure
	*/
	public boolean updateProfile(String accountId, String username, String picture) {
		String tableName = "Profile";
		String columns = "Username=?, Picture=?";
		String where = String.format(this.WHERE, "AccountId=?");
		String[] instance = new String[] {username, picture, accountId};
		boolean success = update(tableName, columns, instance,where);
		return success;
	}
	/*
	-------------------------------
	function: updateAccount
	-------------------------------
	purpose:
		update values in profile table
	return:
		boolean success or failure
	*/
	public boolean updateAccount(String accountId, String email, String hash) {
		String tableName = "Account";
		String columns = "Email=?, Hash=?";
		String where = String.format(this.WHERE, "AccountId=?");
		String[] instance = new String[] {email, hash, accountId};
		boolean success = update(tableName, columns, instance,where);
		return success;
	}
	/*
	-------------------------------
	function: updateGraduation
	-------------------------------
	purpose:
		update values in graduation table
	return:
		boolean success or failure
	*/
	public boolean updateGraduation(String accountId, String year, String semester, String status) {
		String tableName = "Graduation";
		String columns = "Year=?, Semester=?, Graduated=?";
		String where = String.format(this.WHERE, "AccountId=?");
		String[] instance = new String[] {year, semester, status, accountId};
		boolean success = update(tableName, columns, instance,where);
		return success;
	}
	
	/*
	------------------------------------------------------------------------------------------
	Select Account Information
	------------------------------------------------------------------------------------------
	*/
	
	/*
	-------------------------------
	function: getAccountId
	-------------------------------
	purpose:
		get id of account by email
	return:
		string id value
	*/
	public String getAccountId(String email) {
		String tableName = "Account";
		String columns = "accountid";
		String where = String.format(this.WHERE, "Email=?");
		String[] instance = new String[] {email};
		String id = getId(columns,tableName,where,instance);
		return id;
	}
	
	/*
	-------------------------------
	function: getStudentsByStatus
	-------------------------------
	purpose:
		get profile information by graduation status
	return:
		return ResultSet: username, picture
	*/
	public ResultSet getStudentsByStatus(String gradStatus) {
		String desired = "Username,Picture";
		String[] tableNames = new String[] {"Profile","Graduation"};
		String tables = String.join(this.NATURAL_JOIN, tableNames);
		String where = String.format(this.WHERE, "Graduated=?");
		String[] instance = new String[] {gradStatus};
		ResultSet rs = select(desired,tables,where,instance);
		return rs;
	}
	
	/*
	-------------------------------
	function: selectProfileDisplay
	-------------------------------
	purpose:
		select values corresponding to profile display
		select values from account and profile (join tables)
	return:
		ResultSet
	*/
	
	public ResultSet selectProfileDisplay(String accountId) {
		String desired = "Email, Username, Picture, Year, Semester, Graduated";
		String[] tableNames = new String[] {"Account","Profile","Graduation"};
		String tables = String.join(this.NATURAL_JOIN, tableNames);
		String where = String.format(this.WHERE, "AccountId=?");
		String[] instance = new String[] {accountId};
		ResultSet rs = select(desired,tables,where,instance);
		return rs;
	}
	

	/*
	------------------------------------------------------------------------------------------
	Insert Course Information
	------------------------------------------------------------------------------------------
	*/

	/*
	-------------------------------
	function: insertCourse
	-------------------------------
	purpose:
		insert values into course table
	return:
		boolean success or failure
	*/
	public boolean insertCourse(String courseId, String courseName) {
		String tableName = "Course";
		String columns = "CourseId, CName";
		String values = "?, ?";
		String[] instance = new String[] {courseId, courseName};
		boolean success = insert(tableName, columns, values, instance);
		return success;
	}

	/*
	-------------------------------
	function: insertProfessor
	-------------------------------
	purpose:
		insert values into professor table
	return:
		boolean success or failure
	*/
	public boolean insertProfessor(String professorId, String professorName) {
		String tableName = "Professor";
		String columns = "ProfessorId, PName";
		String values = "?, ?";
		String[] instance = new String[] {professorId, professorName};
		boolean success = insert(tableName, columns, values, instance);
		return success;
	}

	/*
	-------------------------------
	function: insertClass
	-------------------------------
	purpose:
		insert values into class table
	return:
		boolean success or failure
	*/
	public boolean insertClass(String classId, String courseId, String professorId) {
		String tableName = "Class";
		String columns = "ClassId, CourseId, ProfessorId";
		String values = "?, ?, ?";
		String[] instance = new String[] {classId, courseId, professorId};
		boolean success = insert(tableName, columns, values, instance);
		return success;
	}
	/*
	------------------------------------------------------------------------------------------
	Update Course Information
	------------------------------------------------------------------------------------------
	*/
	/*
	-------------------------------
	function: updateCourse
	-------------------------------
	purpose:
		update values in course table
	return:
		boolean success or failure
	*/
	public boolean updateCourse(String courseId, String courseName) {
		String tableName = "Course";
		String columns = "CName=?";
		String where = String.format(this.WHERE, "CourseId=?");
		String[] instance = new String[] {courseName, courseId};
		boolean success = update(tableName, columns, instance,where);
		return success;
	}
	/*
	-------------------------------
	function: updateProfessor
	-------------------------------
	purpose:
		update values in professor table
	return:
		boolean success or failure
	*/
	public boolean updateProfessor(String profId, String profName) {
		String tableName = "Professor";
		String columns = "PName=?";
		String where = String.format(this.WHERE, "ProfessorId=?");
		String[] instance = new String[] {profName, profId};
		boolean success = update(tableName, columns, instance,where);
		return success;
	}
	/*
	-------------------------------
	function: updateClass
	-------------------------------
	purpose:
		update values in class table
	return:
		boolean success or failure
	*/
	public boolean updateClass(String classId, String courseId, String profId) {
		String tableName = "Class";
		String columns = "CourseId=?, ProfessorId=?";
		String where = String.format(this.WHERE, "ClassId=?");
		String[] instance = new String[] {courseId, profId, classId};
		boolean success = update(tableName, columns, instance,where);
		return success;
	}
	/*
	------------------------------------------------------------------------------------------
	Select Course Information
	------------------------------------------------------------------------------------------
	*/
	/*
	-------------------------------
	function: getCourseId
	-------------------------------
	purpose:
		get id of course by name
	return:
		string id value
	*/
	public String getCourseId(String courseName) {
		String tableName = "Course";
		String columns = "CourseId";
		String where = String.format(this.WHERE, "cname=?");
		String[] instance = new String[] {courseName};
		
		String id = getId(columns,tableName,where,instance);
		return id;
	}
	/*
	-------------------------------
	function: getProfessorId
	-------------------------------
	purpose:
		get id of professor by name
	return:
		string id value
	*/
	public String getProfessorId(String professorName) {
		String tableName = "Professor";
		String columns = "ProfessorId";
		String where = String.format(this.WHERE, "pname=?");
		String[] instance = new String[] {professorName};
		
		String id = getId(columns,tableName,where,instance);
		return id;
	}
	/*
	-------------------------------
	function: getClassId
	-------------------------------
	purpose:
		get id of class by courseid and professorid
	return:
		string id value
	*/
	public String getClassId(String courseId, String professorId) {
		String tableName = "Class";
		String columns = "ClassId";
		String[] wheres = new String[] {"courseid=?","professorid=?"};
		String where = String.format(this.WHERE,String.join(this.AND, wheres));//String.format(this.AND, String.format(this.WHERE, "courseid=?"),"professorid=?");
		String[] instance = new String[] {courseId,professorId};
		
		String id = getId(columns,tableName,where,instance);
		return id;
	}
	/*
	-------------------------------
	function: selectCourseProfessors
	-------------------------------
	purpose:
		get all professors/classes of a course
	return:
		ResultSet : classid, cname, professorid, pname
	*/
	public ResultSet selectCourseProfessors(String courseId) {
		String columns = "classid,cname,professorid,pname";
		String[] tableNames = new String[] {"Course","Professor","Class"};
		String tables = String.join(this.NATURAL_JOIN, tableNames);
		String where = String.format(this.WHERE,"courseid=?");
		String[] instances = new String[] {courseId};
		//SELECT classid,cname,professorid,pname FROM course NATURAL JOIN class NATURAL JOIN professor WHERE courseId=?
		ResultSet rs = select(columns,tables,where,instances);
		return rs;
	}

	/*
	------------------------------------------------------------------------------------------
	Insert Review Information
	------------------------------------------------------------------------------------------
	*/

	/*
	-------------------------------
	function: insertQuestion
	-------------------------------
	purpose:
		insert values into question table
	return:
		boolean success or failure
	*/
	public boolean insertQuestion(String questionId, String question) {
		String tableName = "Questions";
		String columns = "QuestionId, Question";
		String values = "?, ?";
		String[] instance = new String[] {questionId, question};
		boolean success = insert(tableName, columns, values, instance);
		return success;
	}

	/*
	-------------------------------
	function: insertReview
	-------------------------------
	purpose:
		insert values into review table
	return:
		boolean success or failure
	*/
	public boolean insertReview(String reviewId, String stars, String edit) {
		String tableName = "Review";
		String columns = "ReviewId, Stars, Edit";
		String values = "?, ?, ?";
		String[] instance = new String[] {reviewId, stars, edit};
		boolean success = insert(tableName, columns, values, instance);
		return success;
	}

	/*
	-------------------------------
	function: insertResponse
	-------------------------------
	purpose:
		insert values into response table
	return:
		boolean success or failure
	*/
	public boolean insertResponse(String reviewId, String questionId, String response, String edit) {
		String tableName = "Responses";
		String columns = "ReviewId, QuestionId, Response, Edit";
		String values = "?, ?, ?, ?";
		String[] instance = new String[] {reviewId, questionId, response, edit};
		boolean success = insert(tableName, columns, values, instance);
		return success;
	}

	/*
	-------------------------------
	function: insertPost
	-------------------------------
	purpose:
		insert values into post table
	return:
		boolean success or failure
	*/
	public boolean insertPost(String reviewId, String accountId, String classId, String create) {
		String tableName = "Post";
		String columns = "ReviewId, AccountId, ClassId, Creation";
		String values = "?, ?, ?, ?";
		String[] instance = new String[] {reviewId, accountId, classId, create};
		boolean success = insert(tableName, columns, values, instance);
		return success;
	}
	/*
	------------------------------------------------------------------------------------------
	Update Review Information
	------------------------------------------------------------------------------------------
	*/
	/*
	-------------------------------
	function: updateReview
	-------------------------------
	purpose:
		update values in review table
	return:
		boolean success or failure
	*/
	
	public boolean updateReview(String reviewId, String stars) {
		String edit = this.time.getCurrentTimestamp();
		String tableName = "Review";
		String columns = "Stars=?,Edit=?";
		String where = String.format(this.WHERE, "ReviewId=?");
		String[] instance = new String[] {stars, edit, reviewId};
		boolean success = update(tableName, columns, instance, where);
		return success;
	}
	/*
	-------------------------------
	function: updateResponse
	-------------------------------
	purpose:
		update values in review table
	return:
		boolean success or failure
	*/
	public boolean updateResponse(String reviewId, String questionId, String response) {
		String edit = this.time.getCurrentTimestamp();
		String tableName = "Responses";
		String columns = "response=?,Edit=?";
		String[] wheres = new String[] {"ReviewId=?","QuestionId=?"};
		String where = String.format(this.WHERE, String.join(this.AND, wheres));
		String[] instance = new String[] {response, edit, reviewId, questionId};
		boolean success = update(tableName, columns, instance, where);
		return success;
	}
	
	/*
	------------------------------------------------------------------------------------------
	Select Review Information
	------------------------------------------------------------------------------------------
	*/
	/*
	-------------------------------
	function: selectAvgOfCourse
	-------------------------------
	purpose:
		select average rating of course
	return:
		String
	*/
	
	public String selectAvgOfCourse(String courseId) {
		String desired = String.format(this.AVG,"stars");
		String[] tableNames = new String[] {"Review","Post","Class"};
		String tables = String.join(this.NATURAL_JOIN, tableNames);		
		String where = String.format(this.WHERE, "CourseId=?");
		String[] instance = new String[] {courseId};
		String num = getId(desired,tables,where,instance);
		return num;
	}
	/*
	-------------------------------
	function: selectAvgOfClass
	-------------------------------
	purpose:
		select average rating of course
	return:
		String
	*/
	
	public String selectAvgOfClass(String classId) {
		String desired = String.format(this.AVG,"stars");
		String[] tableNames = new String[] {"Review","Post"};
		String tables = String.join(this.NATURAL_JOIN, tableNames);
		String where = String.format(this.WHERE, "ClassId=?");
		String[] instance = new String[] {classId};
		String num = getId(desired,tables,where,instance);
		return num;
	}
	/*
	-------------------------------
	function: selectReviews
	-------------------------------
	purpose:
		select reviews
	return:
		ResultSet : reviewId
	*/
	
	public ResultSet selectReviews(String classId) {
		String desired = "ReviewId";
		String[] tableNames = new String[] {"Review","Post"};
		String tables = String.join(this.NATURAL_JOIN, tableNames);
		String where = String.format(this.WHERE, "ClassId=?");
		String[] instance = new String[] {classId};
		ResultSet rs = select(desired,tables,where,instance);
		return rs;
	}
	/*
	-------------------------------
	function: selectReviewByStars
	-------------------------------
	purpose:
		select reviews by overall rating
	return:
		ResultSet : reviewId
	*/
	
	public ResultSet selectReviewByStars(String stars, String classId) {
		String desired = "ReviewId";
		String[] tableNames = new String[] {"Review","Post"};
		String tables = String.join(this.NATURAL_JOIN, tableNames);
		String[] wheres = new String[] {"ClassId=?","Stars=?"};
		String where = String.format(this.WHERE, String.join(this.AND, wheres));
		String[] instance = new String[] {classId,stars};
		ResultSet rs = select(desired,tables,where,instance);
		return rs;
	}
	
	/*
	------------------------------------------------------------------------------------------
	Database Interaction
	------------------------------------------------------------------------------------------
	*/
	
	/*
	-------------------------------
	function: getId
	-------------------------------
	params: values related to select statement
	purpose:
		select max id and add 1
	return:
		string id : found id according to statement
	*/
	
	private String getId(String column, String tableName, String where, String[] instances) {
		ResultSet rs = select(column,tableName,where,instances);
		String id = "0";
		if (rs != null) {
			try {
				while (rs.next()) {
					id = rs.getString(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}
	
	/*
	-------------------------------
	function: getUniqueId
	-------------------------------
	params:
		String idCol : name of column associated with id
		String tableName : name of table we wish 
	purpose:
		select max id and add 1
	return:
		string id : next available id
	*/
	
	public String getUniqueId(String idCol, String tableName) {
		String desired = String.format(this.MAX, idCol);
		String where = "";
		String[] instance = new String[] {};
		ResultSet rs = select(desired,tableName,where,instance);
		int id = 1;
		try {
			while (rs.next()) {
				id = rs.getInt(1) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Integer.toString(id);
	}

	/*
	-------------------------------
	function: insert
	-------------------------------
	params:
		String tableName: name of table we wish to insert
		String columns: stringified list of attribute names separated by ", "
		String values: stringified list of ? names separated by ", " (number corresponds to column count)
		String[] instance: array of values to insert
	purpose:
		format insertion query
		create prepared statement
		execute
	return:
		boolean success or failure
	*/
	private boolean insert(String tableName, String columns, String values, String[] instance) {
		String query = String.format(this.INSERT,tableName,columns,values);
		PreparedStatement stmt = prepare(query, instance);
		boolean success = executeUpdate(stmt);
		return success;
	}
	/*
	-------------------------------
	function: update
	-------------------------------
	params:
		String tableName: name of table we wish to update
		String columns: stringified list of attribute names separated by ", "
		String[] instance: array of values to update
		String where: all conditions which must be true (empty string if none)
	purpose:
		format update query
		create prepared statement
		execute
	return:
		boolean success or failure
	*/
	private boolean update(String tableName, String columns, String[] instance,String where) {
		String query = String.format(this.UPDATE,tableName,columns,where);
		PreparedStatement stmt = prepare(query, instance);
		boolean success = executeUpdate(stmt);
		return success;
	}
	/*
	-------------------------------
	function: select
	-------------------------------
	params:
		String columns: stringified list of attribute names separated by ", "
		String tables: stringified list of tables separated by "NATURAL JOIN"
		String where: all conditions which must be true (empty string if none)
		String[] instance: array of values to insert
	purpose:
		format selection query
		create prepared statement
		execute
	return:
		ResultSet
	*/
	private ResultSet select(String columns, String tables, String where, String[] instance) {
		String query = String.format(this.SELECT, columns, tables, where);
		PreparedStatement stmt = prepare(query, instance);
		ResultSet rs = executeSelection(stmt);
		return rs;
	}
	/*
	-------------------------------
	function: prepare
	-------------------------------
	params:
		String query: sql statement
		String[] instance: array of values to insert
	purpose:
		setup prepared statement
		iterate loop to insert desired values in "?" positions
	return:
		PreparedStatement statement
	*/
	private PreparedStatement prepare(String query, String[] instance) {
		System.out.println("Preparing statement: " + query);
		PreparedStatement stmt;
		try {
			stmt = this.conn.prepareStatement(query);
			for (int i=0; i<instance.length; i++) {
				String item = instance[i];
				stmt.setString(i+1, item);
			}
			return stmt;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	-------------------------------
	function: executeUpdate
	-------------------------------
	params:
		PreparedStatement stmt
	purpose:
		execute statement
		handle errors
	return:
		boolean success or failure
	*/
	private boolean executeUpdate(PreparedStatement stmt) {
		try {
			int i = stmt.executeUpdate();
			this.conn.commit();
			if (i > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/*
	-------------------------------
	function: executeSelection
	-------------------------------
	params:
		PreparedStatement stmt
	purpose:
		execute statement
		handle errors
	return:
		ResultSet
	*/
	private ResultSet executeSelection(PreparedStatement stmt) {
		try {
			ResultSet rs = stmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
