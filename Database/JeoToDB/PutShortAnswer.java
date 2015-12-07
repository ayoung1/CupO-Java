//Bryce Fenske

//takes a map of <question, answer> strings and adds them to the short answer portion of the online db
//note: IP of the computer this is running on must be cleared with the host provider

import java.sql.Connection;
import com.mysql.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class PutShortAnswer {
	//abstract out reused code between this and SQLConnection class
	
	private final String URL = "jdbc:mysql://69.195.124.90:3306/";
	private final String USERNAME = "aaronyou";
	private final String PASSWORD = "]=E8x_jkVT+k";
	private final String DATABASE = "aaronyou_Trivia_Maze";
	private final String DRIVER = "com.mysql.jdbc.Driver";
	private final String TABLE = "short_answer";
	
	private HashMap<String, String> shortAnswers;
	private Connection conn = null;
	
	public PutShortAnswer(HashMap<String, String> qs){
		shortAnswers = qs;
	}
	
	public PutShortAnswer(){}
	
	public void setSA(HashMap<String, String> qs)
	{
		shortAnswers = qs;
	}
	
	public void putQuestions() throws SQLException
	{
		Statement statement = conn.createStatement();
		for( String k: shortAnswers.keySet())
		{
			//String temp = k.replace("'", "\\'");
			statement.executeUpdate("insert into " + TABLE + 
					" values ( NULL, '" + k + "','" + ((String) shortAnswers.get(k)) + "')"); //; in sql?
		}
	}
	
	
	public boolean openConnection() throws InstantiationException, ClassNotFoundException
	{
		try{
			Class.forName(DRIVER).newInstance();
		}catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}
		
		try {
			return (conn = DriverManager.getConnection(
					URL+DATABASE+"?useSSL=true", USERNAME, PASSWORD)) != null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void closeConnection() throws SQLException
	{
		if(conn != null)
			conn.close();
	}
}
 