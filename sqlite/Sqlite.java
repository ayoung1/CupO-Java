/*
 * Aaron Young
 */
package sqlite;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

import question.*;

public class Sqlite {
	public static final int TRUE_FALSE = 0;
	public static final int SHORT_ANSWER = 1;
	public static final int MULTIPLE_CHOICE = 2;
	
	private static final int TF_LENGTH = 2;
	private static final int SA_LENGTH = 2;
	private static final int MC_MAX = 6;
	
	private final String truefalse = "true_false";
	private final String shortanswer = "short_answer";
	private final String multiplechoice = "multiple_choice";
	
	private int[] exclusions;
	private int autoIncrement = 0;
	private ArrayList<Question> pulledQuestions;
	private Connection connect;
	private String dbName;
	
	public Sqlite(){
		this.pulledQuestions = new ArrayList<Question>();
	}
	
	public boolean connect(String databaseName){
		this.dbName = databaseName;
		File db = new File(this.dbName);
		boolean existed = db.exists();
		
		try {
	      Class.forName("org.sqlite.JDBC");
	      this.connect = DriverManager.getConnection("jdbc:sqlite:"+databaseName);
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    }
	    System.out.println("Opened database successfully");
	    this.exclusions = new int[0];
	    
	    if(!existed){
	    	this.autoIncrement = 0;
	    	createTables();
	    }else{
	    	setLargestId();
	    }
	    
		return !existed;
	}
	
	private void setLargestId(){
		this.autoIncrement = countArchive();
	}
	
	public void disconnect(){
		try {
			this.connect.close();
			System.out.println("Closed database successfully");
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}
	
	public boolean clearDatabase(){
		File file = new File(this.dbName);
		
		if(!file.exists())
			return false;
		disconnect();
		
		file.delete();
		return true;
	}
	
	private void createTables(){
		createTrueFalse();
		createShortAnswer();
		createMultipleChoice();
	}
	
	private void createTrueFalse(){
		Statement stmt = null;
	    try {
	      stmt = connect.createStatement();
	      String sql = "CREATE TABLE `"+ truefalse +"` ("+
						  "`question_num` int(10) NOT NULL,"+
						  "`answer` varchar(1) NOT NULL,"+
						  "`question` varchar(256) NOT NULL"+
						");"; 
	      stmt.executeUpdate(sql);
	      
	      stmt.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table created successfully");
	}
	
	private void createShortAnswer(){
		Statement stmt = null;
	    try {
	      stmt = connect.createStatement();
	      String sql = "CREATE TABLE `"+ shortanswer +"` ("+
						  "`question_num` int(10) NOT NULL,"+
						  "`answer_components` varchar(50) NOT NULL,"+
						  "`question` varchar(256) NOT NULL"+
						");"; 
	      stmt.executeUpdate(sql);
	      
	      stmt.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table created successfully");
	}
	
	private void createMultipleChoice(){
		Statement stmt = null;
	    try {
	      stmt = connect.createStatement();
	      String sql = "CREATE TABLE `"+ multiplechoice +"` ("+
						  "`question_num` int(10) NOT NULL,"+
						  "`num_options` int(1) DEFAULT '2',"+
						  "`question` varchar(256) NOT NULL,"+
						  "`answer` varchar(50) NOT NULL,"+
						  "`option_1` varchar(50) NOT NULL,"+
						  "`option_2` varchar(50) DEFAULT NULL,"+
						  "`option_3` varchar(50) DEFAULT NULL,"+
						  "`option_4` varchar(50) DEFAULT NULL"+
						  ");";
	      stmt.executeUpdate(sql);
	      
	      stmt.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table created successfully");
	}
	
	public void setExclusions(int[] exclusions){
		this.exclusions = exclusions;
	}
	
	
	public boolean add(int tag, String[] args){
		
		if(tag == Sqlite.TRUE_FALSE){
			if(args.length == TF_LENGTH){
				insertTrueFalse(args);
				return true;
			}else{
				System.err.println("Args length doesn't match tag value");
				System.exit(0);
			}
		}else if(tag == Sqlite.SHORT_ANSWER){
			if(args.length == SA_LENGTH){
				insertShortAnswer(args);
				return true;
			}else{
				System.err.println("Args length doesn't match tag value");
				System.exit(0);
			}
		}else if(tag == Sqlite.MULTIPLE_CHOICE){
			if(args.length == MC_MAX){
				insertMultipleChoice(args);
				return true;
			}else{
				System.err.println("Args length doesn't match tag value");
				System.exit(0);
			}
		}else{
			System.err.println("Not a valid tag");
			System.exit(0);
		}
		
		return false;
	}
	
	private void insertTrueFalse(String[] args){
		String sql = "INSERT INTO "+ truefalse +"(question_num, question, answer)"+
					"VALUES(" + this.autoIncrement++ + ",'" + args[0] + "', '" + args[1] + "')";
		Statement stmt = null;
		
		try {
			stmt = connect.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	
	private void insertShortAnswer(String[] args){
		String sql = "INSERT INTO "+ shortanswer +"(question_num, question, answer_components)"+
				"VALUES(" + this.autoIncrement++ + ",'" + args[0] + "', '" + args[1] + "')";
		Statement stmt = null;
		
		try {
			stmt = connect.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	
	private void insertMultipleChoice(String[] args){
		int options = args.length - 2;
		String sql = "INSERT INTO "+ multiplechoice +"(question_num, num_options, question, answer, option_1, option_2, option_3, option_4)"+
				"VALUES(" + this.autoIncrement++ + ", " + options + ", '" + args[0] + "', '" + args[1] + "', '" + args[2] + "', '" + args[3] + "', '" + args[4] + "', '" + args[5] + "')";
		Statement stmt = null;

		try {
			stmt = connect.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	
	public int countArchive(){
		int count = 0;
		String[] tables = {truefalse, shortanswer, multiplechoice};
		String sql;
		Statement stmt;
		ResultSet set;
		
		for(String table : tables){
			sql = "SELECT COUNT(question_num) FROM " + table;
			
			sql = addException(sql);
			
			try {
				stmt = connect.createStatement();
				stmt.execute(sql);
				set = stmt.getResultSet();
				set.next();
				count += set.getInt(1);
				stmt.close();
			} catch (SQLException e) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			    System.exit(0);
			}
		}
		
		return count;
	}
	
	private String addException(String sql){
		if(this.exclusions.length > 0){
			sql += " WHERE ";
			for(int i = 0; i < this.exclusions.length; i++){
				sql += "question_num <> " + this.exclusions[i];
				if(i != this.exclusions.length-1){
					sql += " AND ";
				}
			}
		}
		
		return sql;
	}
	
	private void pullTrueFalse(){
		String sql;
		Statement stmt;
		ResultSet set;
		Question question;
		sql = "SELECT * FROM " + truefalse;
		sql = addException(sql);
		try{
			stmt = connect.createStatement();
			stmt.execute(sql);
			set = stmt.getResultSet();
			while(set.next()){
				question = new TrueFalse(set.getInt(1), set.getString(3), set.getString(2));
				this.pulledQuestions.add(question);
			}
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	
	private void pullShortAnswer(){
		String sql;
		Statement stmt;
		ResultSet set;
		Question question;
		sql = "SELECT * FROM " + shortanswer;
		sql = addException(sql);
		try{
			stmt = connect.createStatement();
			stmt.execute(sql);
			set = stmt.getResultSet();
			while(set.next()){
				question = new ShortAnswer(set.getInt(1), set.getString(3), set.getString(2));
				this.pulledQuestions.add(question);
			}
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	
	private void pullMultipleChoice(){
		String sql;
		Statement stmt;
		ResultSet set;
		Question question;
		sql = "SELECT * FROM " + multiplechoice;
		sql = addException(sql);
		try{
			stmt = connect.createStatement();
			stmt.execute(sql);
			set = stmt.getResultSet();
			while(set.next()){
				question = new MultipleChoice(set.getInt(1), set.getString(3), set.getString(4), set.getString(5), set.getString(6), set.getString(7), set.getString(8));
				this.pulledQuestions.add(question);
			}
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	
	public ArrayList<Question> queryAll(int minRequired){
		if(countArchive() < minRequired)
			return null;
		this.pulledQuestions.clear();
		
		pullTrueFalse();
		pullShortAnswer();
		pullMultipleChoice();
		
		return this.pulledQuestions;
	}
	
	
	
	public ArrayList<Question> queryRandom(int minRequired, int numberQueried){
		if(this.queryAll(minRequired) == null)
			return null;
		Random rand = new Random();
		int pull;
		ArrayList<Question> temp = new ArrayList<Question>();
		
		for(int i = 0; i < numberQueried; i++){
			pull = rand.nextInt(this.pulledQuestions.size());
			temp.add(this.pulledQuestions.remove(pull));
		}
		
		return temp;
	}
}
