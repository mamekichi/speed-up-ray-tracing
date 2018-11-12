package newMethod;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {

	public int id;
	private Connection connection;
	private Statement statement;
	
	Database(){
		this.id = 1;
		try {
			Class.forName("org.sqlite.JDBC");
			
			this.connection = DriverManager.getConnection("jdbc:sqlite:src/DB/newMethod.db");
			this.statement = connection.createStatement();
			this.statement.setQueryTimeout(30);
		}catch(Exception e) {
			System.out.println(e);			
		}
	}
	
	public void createTable(String dbname, String value){
		try {
			System.out.println(dbname + " is created");
			statement.executeUpdate("create virtual table "+dbname+" USING rtree("+value+");");
		}catch(Exception e) {
			System.out.println("in create Table");
			System.out.println(e);
		}
	}
	
	public void insert(String dbname, String value) {
		try {
			statement.executeUpdate("INSERT INTO "+dbname+" VALUES("+value+");");
			this.id++;
		}catch(Exception e) {
			System.out.println("in insert");
			System.out.println(e);
		}
	}
	
	public ResultSet select(String dbname, String value) {
		ResultSet resultset = null;
		try {
			//System.out.println("SELECT * FROM " +dbname+ " WHERE " +value+ ";");
			resultset = statement.executeQuery("SELECT * FROM (SELECT * FROM " +dbname+ ") WHERE " +value+ ";");
			//resultset = statement.executeQuery("SELECT * FROM " +dbname+ " WHERE " +value+ ";");
		}catch(Exception e) {
			//System.out.println("in select");
			//System.out.println(e);
		}
		return resultset;
	}
}
