package pantherinspectproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	 
	String db_name;
	Connection conn;
	
	Database() {
		this.db_name = null;
		this.conn = null;
	}
	
	public String createURL(String ip, String db_name) {
	    String url = String.format("jdbc:mysql://%s/%s?serverTimezone=UTC",ip,db_name);
	    this.db_name = db_name;
	    return url;
	}
	
	public Connection createConnection(String url, String user, String password) {
		try {
		    Class.forName(JDBC_DRIVER);
			
		    System.out.println("Connecting to database...");
		    
		    Connection conn = DriverManager.getConnection(url,user,password);
		    this.conn = conn;
		    return conn;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getDBName() {
		return this.db_name;
	}
	
	public Connection getConn() {
		return this.conn;
	}
}
